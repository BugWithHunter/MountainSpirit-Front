package com.bughunters.mountainspirit.report.query.service;

import com.bughunters.mountainspirit.report.query.dto.ReportQueryDTO;
import com.bughunters.mountainspirit.report.query.mapper.ReportQueryMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportQueryServiceImplTest {

    @Mock
    private ReportQueryMapper reportQueryMapper;

    @InjectMocks
    private ReportQueryServiceImpl reportQueryService;

    @Test
    void testSelectAllReport_ReturnsList() {
        // given
        ReportQueryDTO r1 = new ReportQueryDTO(1L, LocalDateTime.now().minusDays(2), "Board",
                "admin1", "Spam content", "N", 101L, 1L, 1001L, 201L, 301L, 401L, 0);
        ReportQueryDTO r2 = new ReportQueryDTO(2L, LocalDateTime.now().minusDays(1), "Crew",
                null, "Offensive language", "Y", 102L, 2L, 1002L, 202L, 302L, 402L, 1);

        List<ReportQueryDTO> reports = Arrays.asList(r1, r2);

        when(reportQueryMapper.reportAll()).thenReturn(reports);

        // when
        List<ReportQueryDTO> result = reportQueryService.selectAllReport();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Board", result.get(0).getReportType());
        assertEquals("Y", result.get(1).getIsAccepted());
        verify(reportQueryMapper, times(1)).reportAll();
    }

    @Test
    void testSelectReportsByTypeAndStatus() {
        String reportType = "SPAM";
        String isAccepted = "N";

        ReportQueryDTO r1 = new ReportQueryDTO(1L, LocalDateTime.now().minusDays(2), reportType,
                null, "Spam content", isAccepted, 101L, 1L, 1001L, 201L, 301L, 401L, 0);

        when(reportQueryMapper.findReportsByTypeAndStatus(reportType, isAccepted))
                .thenReturn(List.of(r1));

        List<ReportQueryDTO> result = reportQueryService.selectReportsByTypeAndStatus(reportType, isAccepted);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(reportType, result.get(0).getReportType());
        assertEquals(isAccepted, result.get(0).getIsAccepted());
        verify(reportQueryMapper, times(1)).findReportsByTypeAndStatus(reportType, isAccepted);
    }

    @Test
    void testSelectReportsByMemberId() {
        Long memberId = 101L;

        ReportQueryDTO r1 = new ReportQueryDTO(1L, LocalDateTime.now().minusDays(2), "SPAM",
                null, "Spam content", "N", memberId, 1L, 1001L, 201L, 301L, 401L, 0);

        when(reportQueryMapper.findReportsByMemberId(memberId)).thenReturn(List.of(r1));

        List<ReportQueryDTO> result = reportQueryService.selectReportsByMemberId(memberId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(memberId.longValue(), result.get(0).getReportedId());
        verify(reportQueryMapper, times(1)).findReportsByMemberId(memberId);
    }
}
