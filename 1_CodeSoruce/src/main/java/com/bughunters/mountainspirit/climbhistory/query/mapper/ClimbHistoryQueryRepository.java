package com.bughunters.mountainspirit.climbhistory.query.mapper;

import com.bughunters.mountainspirit.climbhistory.query.dto.FindClimbCheckQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.dto.RequestStartClimbMountainQueryDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClimbHistoryQueryRepository {
    FindClimbCheckQueryDTO selectClimbCheckByMember(RequestStartClimbMountainQueryDTO request);
}
