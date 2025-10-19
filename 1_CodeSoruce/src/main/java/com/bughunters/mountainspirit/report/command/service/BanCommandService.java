package com.bughunters.mountainspirit.report.command.service;

import com.bughunters.mountainspirit.report.command.dto.BanAnnotationRequestDTO;

public interface BanCommandService {
    void addAnnotation(BanAnnotationRequestDTO dto, Long adminId);
}
