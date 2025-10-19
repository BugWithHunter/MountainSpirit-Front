package com.bughunters.mountainspirit.report.command.service;

import com.bughunters.mountainspirit.report.command.dto.BlacklistRequestDTO;

public interface BlacklistCommandService {
    void addComment(BlacklistRequestDTO dto, Long adminId);
}
