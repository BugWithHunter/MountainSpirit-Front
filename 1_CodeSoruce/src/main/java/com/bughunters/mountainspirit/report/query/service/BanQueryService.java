package com.bughunters.mountainspirit.report.query.service;

import com.bughunters.mountainspirit.report.command.dto.ReportMemberDTO;
import com.bughunters.mountainspirit.report.query.dto.BanQueryDTO;

import java.util.List;

public interface BanQueryService {
    List<BanQueryDTO> selectAllBan(int page, int size);

}
