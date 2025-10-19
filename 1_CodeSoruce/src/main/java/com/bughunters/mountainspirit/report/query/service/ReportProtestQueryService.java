package com.bughunters.mountainspirit.report.query.service;

import com.bughunters.mountainspirit.report.query.dto.ReportProtestQueryDTO;

import java.util.List;

public interface ReportProtestQueryService {
    List<ReportProtestQueryDTO> selectAllReportProtest(int page, int size);

    List<ReportProtestQueryDTO> selectReportProtestByReportedId(Long reportedId, int page, int size);
}
