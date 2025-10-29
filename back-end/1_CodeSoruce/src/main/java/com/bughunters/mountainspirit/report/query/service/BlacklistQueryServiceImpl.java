package com.bughunters.mountainspirit.report.query.service;

import com.bughunters.mountainspirit.report.query.dto.BlacklistByIdDTO;
import com.bughunters.mountainspirit.report.query.dto.BlacklistQueryDTO;
import com.bughunters.mountainspirit.report.query.mapper.BlacklistQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlacklistQueryServiceImpl implements BlacklistQueryService {

    private final BlacklistQueryMapper blacklistQueryMapper;

    @Autowired
    public BlacklistQueryServiceImpl(BlacklistQueryMapper blacklistQueryMapper) {
        this.blacklistQueryMapper = blacklistQueryMapper;
    }

    @Override
    public List<BlacklistQueryDTO> getAllBlacklist(int page, int size) {
        int offset = (page - 1) * size;
        return blacklistQueryMapper.selectAllBlacklist(size, offset);
    }

    @Override
    public List<BlacklistByIdDTO> getBlacklistById(Long id) {
        return blacklistQueryMapper.selectBlacklistById(id);
    }
}
