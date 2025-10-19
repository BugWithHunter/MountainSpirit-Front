package com.bughunters.mountainspirit.report.query.mapper;

import com.bughunters.mountainspirit.report.query.dto.BlacklistQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface BlacklistQueryMapper {
    List<BlacklistQueryDTO> selectAllBlacklist(
            @Param("limit") Integer limit,
            @Param("offset") Integer offset
    );
}
