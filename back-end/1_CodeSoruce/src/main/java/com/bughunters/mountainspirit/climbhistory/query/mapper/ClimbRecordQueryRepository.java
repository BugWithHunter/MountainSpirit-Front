package com.bughunters.mountainspirit.climbhistory.query.mapper;

import com.bughunters.mountainspirit.climbhistory.query.dto.SelectClimbingRecordMonthlyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClimbRecordQueryRepository {
    List<SelectClimbingRecordMonthlyDTO> SelectClimbingRecordMonthly(Long id);
}
