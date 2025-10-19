package com.bughunters.mountainspirit.report.query.service;

import com.bughunters.mountainspirit.report.query.dto.ReportQueryDTO;

import java.util.List;

public interface ReportQueryService {
    List<ReportQueryDTO> selectAllReport();

    List<ReportQueryDTO> searchReportsByType(String reportType);

    List<ReportQueryDTO> selectReportsByTypeAndStatus(String reportType, String isAccepted, int page, int size);
    List<ReportQueryDTO> selectReportsByMemberId(Long memberId);
}
