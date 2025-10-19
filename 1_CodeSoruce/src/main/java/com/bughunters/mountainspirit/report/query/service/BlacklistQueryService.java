package com.bughunters.mountainspirit.report.query.service;

import com.bughunters.mountainspirit.report.query.dto.BlacklistQueryDTO;

import java.util.List;

public interface BlacklistQueryService {
    List<BlacklistQueryDTO> getAllBlacklist(int page, int size);
}
