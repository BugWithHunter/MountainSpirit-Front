package com.bughunters.mountainspirit.report.command.service;

import com.bughunters.mountainspirit.member.command.entity.Member;
import com.bughunters.mountainspirit.report.command.dto.ReportRequestCommandDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportResponseCommandDTO;
import com.bughunters.mountainspirit.report.command.entity.*;
import com.bughunters.mountainspirit.report.command.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

// 미완성 테스트
public class ReportCommandServiceImplTest {

    private ReportCommandRepository reportCommandRepository;
    private ReportCategoryCommandRepository reportCategoryCommandRepository;
    private ReportMemberCommandRepository reportMemberCommandRepository;
    private BanCommandRepository banCommandRepository;
    private BlacklistCommandRepository blacklistCommandRepository;
    private ModelMapper modelMapper;

    private ReportCommandServiceImpl reportCommandService;

    @BeforeEach
    void setUp() {
        reportCommandRepository = mock(ReportCommandRepository.class);
        reportCategoryCommandRepository = mock(ReportCategoryCommandRepository.class);
        reportMemberCommandRepository = mock(ReportMemberCommandRepository.class);
        banCommandRepository = mock(BanCommandRepository.class);
        blacklistCommandRepository = mock(BlacklistCommandRepository.class);

        // ModelMapper 초기화
        modelMapper = new ModelMapper();

        // DTO -> Entity 매핑 시 id는 건너뛰도록 설정
        modelMapper.typeMap(ReportRequestCommandDTO.class, ReportCommandEntity.class)
                .addMappings(mapper -> mapper.skip(ReportCommandEntity::setId));

        reportCommandService = new ReportCommandServiceImpl(
                reportCommandRepository,
                reportCategoryCommandRepository,
                reportMemberCommandRepository,
                banCommandRepository,
                blacklistCommandRepository,
                modelMapper
        );
    }

    @Test
    void createReport_NormalCase() {
        ReportRequestCommandDTO request = new ReportRequestCommandDTO();
        request.setReportedId(1L);
        request.setCategoryId(10L);

        Member member = new Member();
        member.setId(1L);
        member.setBanCnt(0);

        ReportCategoryCommandEntity category = new ReportCategoryCommandEntity();
        category.setId(10L);
        category.setCountStandard(5);

        when(reportMemberCommandRepository.findById(1L))
                .thenReturn(Optional.of(member));
        when(reportCategoryCommandRepository.findById(10L))
                .thenReturn(Optional.of(category));
        when(reportCommandRepository.countByReportedIdAndSuspensionCycleAndCategoryId(anyLong(), anyInt(), anyLong()))
                .thenReturn(1L);
        when(reportCommandRepository.findByReportedIdAndSuspensionCycle(anyLong(), anyInt()))
                .thenReturn(List.of(new ReportCommandEntity()));

        ReportResponseCommandDTO response = reportCommandService.createReport(request);

        assertThat(response).isNotNull();
        assertThat(response.getResult()).isEqualTo("NORMAL");

        verify(reportCommandRepository, times(1)).save(any(ReportCommandEntity.class));
    }

    @Test
    void createReport_ToBlacklistDirectly() {
        ReportRequestCommandDTO request = new ReportRequestCommandDTO();
        request.setReportedId(2L);
        request.setCategoryId(20L);

        Member member = new Member();
        member.setId(2L);
        member.setBanCnt(0);

        ReportCategoryCommandEntity category = new ReportCategoryCommandEntity();
        category.setId(20L);
        category.setCountStandard(0); // 블랙리스트 조건

        when(reportMemberCommandRepository.findById(2L))
                .thenReturn(Optional.of(member));
        when(reportCategoryCommandRepository.findById(20L))
                .thenReturn(Optional.of(category));
        when(reportCommandRepository.countByReportedIdAndSuspensionCycleAndCategoryId(anyLong(), anyInt(), anyLong()))
                .thenReturn(0L);

        ReportResponseCommandDTO response = reportCommandService.createReport(request);

        assertThat(response.getResult()).isEqualTo("BLACKLIST");
        verify(blacklistCommandRepository, times(1)).save(any(BlacklistCommandEntity.class));
    }

    @Test
    void createReport_ToBanWhenCountReached() {
        ReportRequestCommandDTO request = new ReportRequestCommandDTO();
        request.setReportedId(3L);
        request.setCategoryId(30L);

        Member member = new Member();
        member.setId(3L);
        member.setBanCnt(0);

        ReportCategoryCommandEntity category = new ReportCategoryCommandEntity();
        category.setId(30L);
        category.setCountStandard(3);

        when(reportMemberCommandRepository.findById(3L))
                .thenReturn(Optional.of(member));
        when(reportCategoryCommandRepository.findById(30L))
                .thenReturn(Optional.of(category));
        when(reportCommandRepository.countByReportedIdAndSuspensionCycleAndCategoryId(anyLong(), anyInt(), anyLong()))
                .thenReturn(3L); // 기준 달성
        when(banCommandRepository.countByUserId(anyLong()))
                .thenReturn(0);

        ReportResponseCommandDTO response = reportCommandService.createReport(request);

        assertThat(response.getResult()).isEqualTo("BAN");
        verify(banCommandRepository, times(1)).save(any(BanCommandEntity.class));
        verify(reportMemberCommandRepository, atLeastOnce()).save(any(Member.class));
    }
}
