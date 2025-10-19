package com.bughunters.mountainspirit.report.query.service;

import com.bughunters.mountainspirit.report.query.dto.ReportQueryDTO;

import java.util.List;

public interface ReportQueryService {
    List<ReportQueryDTO> selectAllReport(int page, int size);

    List<ReportQueryDTO> searchReportsByType(String reportType,int page, int size);

    List<ReportQueryDTO> selectReportsByTypeAndStatus(String reportType, String isAccepted, int page, int size);
    List<ReportQueryDTO> selectReportsByMemberId(Long memberId, int page, int size);
}
