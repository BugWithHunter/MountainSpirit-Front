package com.bughunters.mountainspirit.report.command.service;

import com.bughunters.mountainspirit.member.command.entity.Member;
import com.bughunters.mountainspirit.report.command.dto.ReportRequestCommandDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportResponseCommandDTO;
import com.bughunters.mountainspirit.report.command.entity.*;
import com.bughunters.mountainspirit.report.command.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        // suspensionCycle은 member의 bancnt와 동일
        int suspensionCycle = member.getBanCnt();

        // Report 엔티티 매핑
        ReportCommandEntity rce = modelMapper.map(reportRequestCommandDTO, ReportCommandEntity.class);
        rce.setReportDate(LocalDateTime.now());
        rce.setSuspensionCycle(suspensionCycle);
        rce.setIsAccepted("N");

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
                            .adminId(reportRequestCommandDTO.getAdminId())
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

        if (banCount == 1) {
            endDate = startDate.plusDays(7);
        } else if (banCount == 2) {
            endDate = startDate.plusDays(15);
        }  else if (banCount == 3) {
            endDate = startDate.plusDays(30);
        }  else if (banCount == 4) {
            BlacklistCommandEntity blcEntity = BlacklistCommandEntity.builder()
                    .createDate(LocalDateTime.now())
                    .memberId(rrdto.getReportedId())
                    .adminId(rrdto.getAdminId())
                    .build();
            blacklistCommandRepository.save(blcEntity);

            // 회원 banCnt도 증가
            member.setBanCnt(member.getBanCnt() + 1);
            reportMemberCommandRepository.save(member);
        }

        BanCommandEntity bcEntity = BanCommandEntity.builder()
                .startDate(startDate)
                .endDate(endDate)
                .userId(rrdto.getReportedId())
                .adminId(rrdto.getAdminId())
                .build();

        // 회원 banCnt도 증가
        member.setBanCnt(member.getBanCnt() + 1);
        reportMemberCommandRepository.save(member);
    }
}
