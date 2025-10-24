package com.bughunters.mountainspirit.report.query.service;

import com.bughunters.mountainspirit.report.command.dto.ReportMemberDTO;
import com.bughunters.mountainspirit.report.query.dto.BanByIdDTO;
import com.bughunters.mountainspirit.report.query.dto.BanQueryDTO;
import com.bughunters.mountainspirit.report.query.mapper.BanQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BanQueryServiceImpl implements BanQueryService {

    private final BanQueryMapper banQueryMapper;

    @Autowired
    public BanQueryServiceImpl(BanQueryMapper banQueryMapper) {
        this.banQueryMapper = banQueryMapper;
    }

    @Override
    public List<BanQueryDTO> selectAllBan(int page, int size) {
        int offset = (page - 1) * size;
        return banQueryMapper.selectAllBan(size, offset);
    }

    @Override
    public List<BanByIdDTO> selectByIdBan(Long id) {
        return banQueryMapper.selectByIdBan(id);
    }


}
