package com.bughunters.mountainspirit.report.query.service;

import com.bughunters.mountainspirit.report.query.dto.ReportProtestQueryDTO;

import java.util.List;

public interface ReportProtestQueryService {
    List<ReportProtestQueryDTO> selectAllReportProtest();

    List<ReportProtestQueryDTO> selectReportProtestByReportedId(Long reportedId);
}
