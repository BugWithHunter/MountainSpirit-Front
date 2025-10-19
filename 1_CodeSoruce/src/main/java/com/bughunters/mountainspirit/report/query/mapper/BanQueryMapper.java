package com.bughunters.mountainspirit.report.query.mapper;

import com.bughunters.mountainspirit.report.query.dto.BanQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BanQueryMapper {
    List<BanQueryDTO> selectAllBan(@Param("limit") Integer limit,
                                   @Param("offset") Integer offset);

}
