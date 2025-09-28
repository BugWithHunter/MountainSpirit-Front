package com.bughunters.mountainspirit.report.command.service;

import com.bughunters.mountainspirit.member.command.entity.Member;
import com.bughunters.mountainspirit.report.command.dto.ReportRequestCommandDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportResponseCommandDTO;
import com.bughunters.mountainspirit.report.command.entity.*;
import com.bughunters.mountainspirit.report.command.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportCommandServiceImpl implements ReportCommandService {

    private ReportCommandRepository reportCommandRepository;
    private ReportCategoryCommandRepository reportCategoryCommandRepository;
    private ReportMemberCommandRepository reportMemberCommandRepository;
    private BanCommandRepository banCommandRepository;
    private BlacklistCommandRepository blacklistCommandRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ReportCommandServiceImpl(ReportCommandRepository reportCommandRepository,
                                    ReportCategoryCommandRepository reportCategoryCommandRepository,
                                    ReportMemberCommandRepository reportMemberCommandRepository,
                                    BanCommandRepository banCommandRepository,
                                    BlacklistCommandRepository blacklistCommandRepository,
                                    ModelMapper modelMapper){
        this.reportCommandRepository = reportCommandRepository;
        this.reportCategoryCommandRepository = reportCategoryCommandRepository;
        this.reportMemberCommandRepository = reportMemberCommandRepository;
        this.banCommandRepository = banCommandRepository;
        this.blacklistCommandRepository = blacklistCommandRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public ReportResponseCommandDTO createReport(ReportRequestCommandDTO reportRequestCommandDTO) {

        Member member = reportMemberCommandRepository
                .findById(reportRequestCommandDTO.getReportedId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

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
        rce.setIsAccepted("N");

        // 현재 코드는 생성되는 기준에서 다른 테이블의 생성에 코드가 작성되었는데
        // 추후에 상태가 변경되었을 때를 기준으로 정지테이블과 블랙리스트 테이블 생성 예정

        reportCommandRepository.save(rce);

        // ReportCategory 가져오기
        ReportCategoryCommandEntity rcce = reportCategoryCommandRepository
                .findById(reportRequestCommandDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        String result = "NORMAL";
        
        if(rcce.getCountStandard() == 0) {
            // 블랙리스트 처리
            BlacklistCommandEntity bce =
                    BlacklistCommandEntity.builder()
                            .createDate(LocalDateTime.now())
                            .memberId(reportRequestCommandDTO.getReportedId())
                            .build();
            blacklistCommandRepository.save(bce);
            result = "BLACKLIST";
        }

        long instanceCntNum = reportCommandRepository.countByReportedIdAndSuspensionCycleAndCategoryId(
                reportRequestCommandDTO.getReportedId(), suspensionCycle, reportRequestCommandDTO.getCategoryId()
        );

        if (instanceCntNum >= rcce.getCountStandard()) {
            createBan(member ,reportRequestCommandDTO);
            result = "BAN";
        }
                
        List<ReportCommandEntity> rcelist = reportCommandRepository
                .findByReportedIdAndSuspensionCycle(
                        reportRequestCommandDTO.getReportedId(), suspensionCycle
                );

        if (rcelist.size() == 10) {
            createBan(member, reportRequestCommandDTO);
            result = "BAN";
        }

        ReportResponseCommandDTO rrcdto = modelMapper.map(rce, ReportResponseCommandDTO.class);
        rrcdto.setResult(result);

        return rrcdto;
    }

    private void createBan(Member member, ReportRequestCommandDTO rrdto) {
        int banCount = banCommandRepository.countByUserId(rrdto.getReportedId()) + 1;

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
            BlacklistCommandEntity blcEntity = BlacklistCommandEntity.builder()
                    .createDate(LocalDateTime.now())
                    .memberId(rrdto.getReportedId())
                    .build();
            blacklistCommandRepository.save(blcEntity);

            // 회원 banCnt도 증가
            member.setBanCnt(member.getBanCnt() + 1);
            // 회원 블랙리스트 상태로 변경 (ReportCategory 적용은 추후개선)
            member.setMemStsId(5L);
            reportMemberCommandRepository.save(member);
            return;
        }

        BanCommandEntity bcEntity = BanCommandEntity.builder()
                .startDate(startDate)
                .endDate(endDate)
                .userId(rrdto.getReportedId())
                .build();
        banCommandRepository.save(bcEntity);

        // 회원 banCnt도 증가
        member.setBanCnt(member.getBanCnt() + 1);
        member.setMemStsId(3L);
        reportMemberCommandRepository.save(member);
    }
}
