package com.bughunters.mountainspirit.report.query.service;

import com.bughunters.mountainspirit.report.query.dto.ReportQueryDTO;

import java.util.List;

public interface ReportQueryService {
    List<ReportQueryDTO> selectAllReport();
    List<ReportQueryDTO> selectReportsByTypeAndStatus(String reportType, String isAccepted);
    List<ReportQueryDTO> selectReportsByMemberId(Long memberId);
}
