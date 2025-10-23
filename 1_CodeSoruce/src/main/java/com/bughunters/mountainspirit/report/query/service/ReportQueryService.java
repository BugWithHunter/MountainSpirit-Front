package com.bughunters.mountainspirit.report.query.service;

import com.bughunters.mountainspirit.report.query.dto.AppealDetailByIdDTO;
import com.bughunters.mountainspirit.report.query.dto.ReportCheckDTO;
import com.bughunters.mountainspirit.report.query.dto.ReportDetailDTO;
import com.bughunters.mountainspirit.report.query.dto.ReportQueryDTO;

import java.util.List;

public interface ReportQueryService {
    List<ReportQueryDTO> selectAllReport(int page, int size);

    List<ReportCheckDTO> searchReportsByType(String reportType,int page, int size);

    List<ReportCheckDTO> selectReportsByTypeAndStatus(String reportType, String isAccepted, int page, int size);
    List<ReportDetailDTO> selectReportDetailById(Long id);
    List<ReportQueryDTO> selectReportsByMemberId(Long memberId, int page, int size);

}
