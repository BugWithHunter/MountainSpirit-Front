package com.bughunters.mountainspirit.report.command.service;

import com.bughunters.mountainspirit.report.command.dto.*;
import com.bughunters.mountainspirit.report.command.entity.*;
import com.bughunters.mountainspirit.report.command.infrastructure.MemberFeignClient;
import com.bughunters.mountainspirit.report.command.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportCommandServiceImpl implements ReportCommandService {

    private final ReportCommandRepository reportCommandRepository;
    private final ReportCategoryCommandRepository reportCategoryCommandRepository;
    private final BanCommandRepository banCommandRepository;
    private final BlacklistCommandRepository blacklistCommandRepository;
    private final MemberFeignClient memberFeignClient;
    private final ModelMapper modelMapper;

    @Autowired
    public ReportCommandServiceImpl(ReportCommandRepository reportCommandRepository,
                                    ReportCategoryCommandRepository reportCategoryCommandRepository,
                                    BanCommandRepository banCommandRepository,
                                    BlacklistCommandRepository blacklistCommandRepository,
                                    MemberFeignClient memberFeignClient,
                                    ModelMapper modelMapper){
        this.reportCommandRepository = reportCommandRepository;
        this.reportCategoryCommandRepository = reportCategoryCommandRepository;
        this.banCommandRepository = banCommandRepository;
        this.blacklistCommandRepository = blacklistCommandRepository;
        this.memberFeignClient = memberFeignClient;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public ReportResponseCommandDTO createReport(ReportRequestCommandDTO reportRequestCommandDTO) {

        ReportMemberDTO member = memberFeignClient
                .getMemberInfo(reportRequestCommandDTO.getReportedId());

        // 회원 상태 체크: 정지(3), 블랙리스트(5)면 신고 불가
        if (member.getMemStsId() != null && (member.getMemStsId() == 3L || member.getMemStsId() == 5L)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "현재 상태에서는 신고가 불가능합니다.");
        }

        // suspensionCycle은 member의 bancnt와 동일
        int suspensionCycle = member.getBanCnt();

        // Report 엔티티 매핑
        ReportCommandEntity rce = modelMapper.map(reportRequestCommandDTO, ReportCommandEntity.class);
        rce.setReportDate(LocalDateTime.now());
        rce.setSuspensionCycle(suspensionCycle);
        rce.setIsAccepted(ReportIsAccepted.N);

        // 현재 코드는 생성되는 기준에서 다른 테이블의 생성에 코드가 작성되었는데
        // 추후에 상태가 변경되었을 때를 기준으로 정지테이블과 블랙리스트 테이블 생성 예정

        reportCommandRepository.save(rce);

        //응답
        ReportResponseCommandDTO rrcdto = modelMapper.map(rce, ReportResponseCommandDTO.class);
        rrcdto.setResult("PENDING");
        return rrcdto;
    }

    @Override
    @Transactional
    public ReportResponseCommandDTO updateReportStatus(Long reportedId, ReportIsAccepted status) {
        // report 조회
        ReportCommandEntity report = reportCommandRepository.findById(reportedId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "신고를 찾을 수 없습니다"));

        // 상태 업데이트
        report.setIsAccepted(status);
        reportCommandRepository.save(report);

        String result = "UPDATED";

        if (status == ReportIsAccepted.Y) {
            // 신고 승인일 때만 제재 처리
            ReportMemberDTO member = memberFeignClient.getMemberInfo(report.getReportedId());

            ReportCategoryCommandEntity rcce = reportCategoryCommandRepository
                    .findById(report.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "카테고리를 찾을 수 없습니다"));

            long instanceCntNum = reportCommandRepository.countByReportedIdAndSuspensionCycleAndCategoryIdAndIsAccepted(
                    report.getReportedId(),
                    report.getSuspensionCycle(),
                    report.getCategoryId(),
                    ReportIsAccepted.Y
        );

        // 블랙리스트 조건 확인
        if (rcce.getCountStandard() == 0) {
            createBlacklist(member, report);
            result = "BLACKLIST";
        } else if (rcce.getCountStandard() == 1 && instanceCntNum >= 2) {
            createBlacklist(member, report);
            result = "BLACKLIST";
        } else if (rcce.getCountStandard() == 2 && instanceCntNum >= 3) {
            createBlacklist(member, report);
            result = "BLACKLIST";
        }

        // 블랙리스트가 아닌 경우 항상 BAN 적용
        if (member.getMemStsId() == null || member.getMemStsId() != 5L) {
            createBan(member, report);
            if (!"BLACKLIST".equals(result)) {
                result = "BAN";
                }
            }
        }

        ReportResponseCommandDTO rrcdto = modelMapper.map(report, ReportResponseCommandDTO.class);
        rrcdto.setResult(result);
        return rrcdto;
    }



    private void createBan(ReportMemberDTO member, ReportCommandEntity rcdto) {
        int banCount = banCommandRepository.countByUserId(rcdto.getReportedId()) + 1;

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = null;

        // 블랙리스트가 이미 적용되어 있으면 BAN 무시
        if (member.getMemStsId() != null && member.getMemStsId() == 5) {
            return;
        }

        if (banCount == 1) {
            endDate = startDate.plusDays(7);
        } else if (banCount == 2) {
            endDate = startDate.plusDays(15);
        }  else if (banCount == 3) {
            endDate = startDate.plusDays(30);
        }  else if (banCount >= 4) {
            // 밴 인스턴스 횟수가 4회 이상일 때 블랙리스트 테이블 생성
            createBlacklist(member, rcdto);
            return;
        }

        BanCommandEntity bcEntity = BanCommandEntity.builder()
                .startDate(startDate)
                .endDate(endDate)
                .userId(rcdto.getReportedId())
                .build();
        banCommandRepository.save(bcEntity);

        // Member 상태 업데이트 Feign 호출
        ReportMemberUpdateDTO updateDTO = new ReportMemberUpdateDTO(member.getId(), 3L, banCount);
        memberFeignClient.updateMemberStatus(member.getId(), updateDTO);
    }

    private void createBlacklist(ReportMemberDTO member, ReportCommandEntity report) {
        if (member.getMemStsId() != null && member.getMemStsId() == 5L) {
            return; // 이미 블랙리스트 상태면 중복 방지
        }

        BlacklistCommandEntity bce = BlacklistCommandEntity.builder()
                .createDate(LocalDateTime.now())
                .memberId(report.getReportedId())
                .build();
        blacklistCommandRepository.save(bce);

        // Member 상태 업데이트 Feign 호출
        ReportMemberUpdateDTO updateDTO = new ReportMemberUpdateDTO(member.getId(), 5L, member.getBanCnt());
        memberFeignClient.updateMemberStatus(member.getId(), updateDTO);
    }
}
