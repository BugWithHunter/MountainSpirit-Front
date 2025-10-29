package com.bughunters.mountainspirit.report.command.service;

import com.bughunters.mountainspirit.report.command.dto.BanAnnotationRequestDTO;
import com.bughunters.mountainspirit.report.command.entity.BanCommandEntity;

public interface BanCommandService {
    void addAnnotation(Long banId, String annotation, Long adminId);

    BanCommandEntity getBanById(Long banId);
}
