package com.bughunters.mountainspirit.report.query.mapper;

import com.bughunters.mountainspirit.report.query.dto.BlacklistQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface BlacklistQueryMapper {
    List<BlacklistQueryDTO> selectAllBlacklist();
}
