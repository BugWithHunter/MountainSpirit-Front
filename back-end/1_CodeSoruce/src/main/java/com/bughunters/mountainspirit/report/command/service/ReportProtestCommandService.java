package com.bughunters.mountainspirit.report.command.service;

import com.bughunters.mountainspirit.report.command.dto.ReportProtestRequestDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportProtestResponseDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportProtestStatusUpdateDTO;
import org.springframework.transaction.annotation.Transactional;

public interface ReportProtestCommandService {
    ReportProtestResponseDTO createReportProtest(ReportProtestRequestDTO dto);

    @Transactional
    ReportProtestResponseDTO updateProtestStatus(Long protestId, Long adminId, ReportProtestStatusUpdateDTO dto);
}
