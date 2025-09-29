package com.bughunters.mountainspirit.report.command.service;

import com.bughunters.mountainspirit.member.command.entity.Member;
import com.bughunters.mountainspirit.report.command.dto.ReportIsAccepted;
import com.bughunters.mountainspirit.report.command.dto.ReportRequestCommandDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportResponseCommandDTO;
import com.bughunters.mountainspirit.report.command.entity.*;
import com.bughunters.mountainspirit.report.command.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


// 미완성 테스트
@ExtendWith(MockitoExtension.class)
public class ReportCommandServiceImplTest {

    @Mock
    private ReportCommandRepository reportCommandRepository;

    @Mock
    private ReportCategoryCommandRepository reportCategoryCommandRepository;

    @Mock
    private ReportMemberCommandRepository reportMemberCommandRepository;

    @Mock
    private BanCommandRepository banCommandRepository;

    @Mock
    private BlacklistCommandRepository blacklistCommandRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReportCommandServiceImpl reportCommandService;

    @Test
    void testCreateReport_Success() {
        ReportRequestCommandDTO dto = new ReportRequestCommandDTO();
        dto.setReportedId(1L);

        Member member = new Member();
        member.setMemStsId(null);
        member.setBanCnt(0);

        ReportCommandEntity entity = new ReportCommandEntity();
        when(reportMemberCommandRepository.findById(1L)).thenReturn(Optional.of(member));
        when(modelMapper.map(dto, ReportCommandEntity.class)).thenReturn(entity);
        when(modelMapper.map(entity, ReportResponseCommandDTO.class)).thenReturn(new ReportResponseCommandDTO());

        var result = reportCommandService.createReport(dto);

        assertNotNull(result);
        assertEquals("PENDING", result.getResult());
        verify(reportCommandRepository, times(1)).save(entity);
    }

    @Test
    void testCreateReport_Fail_BannedMember() {
        ReportRequestCommandDTO dto = new ReportRequestCommandDTO();
        dto.setReportedId(1L);

        Member member = new Member();
        member.setMemStsId(3L); // 정지 상태

        when(reportMemberCommandRepository.findById(1L)).thenReturn(Optional.of(member));

        assertThrows(ResponseStatusException.class, () -> reportCommandService.createReport(dto));
    }

    @Test
    void testUpdateReportStatus_Approve_Ban() {
        Long reportId = 1L;
        ReportCommandEntity report = new ReportCommandEntity();
        report.setReportedId(1L);
        report.setCategoryId(1L);
        report.setSuspensionCycle(0);
        report.setIsAccepted(ReportIsAccepted.N);

        Member member = new Member();
        member.setMemStsId(null);
        member.setBanCnt(0);

        ReportCategoryCommandEntity category = new ReportCategoryCommandEntity();
        category.setCountStandard(0);

        when(reportCommandRepository.findById(reportId)).thenReturn(Optional.of(report));
        when(reportMemberCommandRepository.findById(1L)).thenReturn(Optional.of(member));
        when(reportCategoryCommandRepository.findById(1L)).thenReturn(Optional.of(category));
        when(reportCommandRepository.countByReportedIdAndSuspensionCycleAndCategoryIdAndIsAccepted(anyLong(), anyInt(), anyLong(), any()))
                .thenReturn(0L);
        when(modelMapper.map(report, ReportResponseCommandDTO.class)).thenReturn(new ReportResponseCommandDTO());

        var response = reportCommandService.updateReportStatus(reportId, ReportIsAccepted.Y);

        assertNotNull(response);
        assertEquals("BLACKLIST", response.getResult());
        verify(blacklistCommandRepository, times(1)).save(any());
    }

    @Test
    void testUpdateReportStatus_Reject() {
        Long reportId = 1L;
        ReportCommandEntity report = new ReportCommandEntity();
        report.setIsAccepted(ReportIsAccepted.N);

        when(reportCommandRepository.findById(reportId)).thenReturn(Optional.of(report));
        when(modelMapper.map(report, ReportResponseCommandDTO.class)).thenReturn(new ReportResponseCommandDTO());

        var response = reportCommandService.updateReportStatus(reportId, ReportIsAccepted.N);

        assertNotNull(response);
        assertEquals("UPDATED", response.getResult());
    }

}
