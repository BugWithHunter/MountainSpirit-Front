package com.bughunters.mountainspirit.report.command.service;

import com.bughunters.mountainspirit.report.command.dto.ReportIsAccepted;
import com.bughunters.mountainspirit.report.command.dto.ReportRequestCommandDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportResponseCommandDTO;

public interface ReportCommandService {
    ReportResponseCommandDTO createReport(ReportRequestCommandDTO reportRequestCommandDTO);
    ReportResponseCommandDTO updateReportStatus(Long reportedId, ReportIsAccepted status);
}
