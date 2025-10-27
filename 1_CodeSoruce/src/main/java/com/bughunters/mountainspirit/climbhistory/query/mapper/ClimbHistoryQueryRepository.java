package com.bughunters.mountainspirit.climbhistory.query.mapper;

import com.bughunters.mountainspirit.climbhistory.query.dto.FindClimbCheckQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.dto.RequestStartClimbMountainQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClimbHistoryQueryRepository {
    FindClimbCheckQueryDTO selectClimbCheckByMember(RequestStartClimbMountainQueryDTO request);

    List<RequestStartClimbMountainQueryDTO> selectAllClimbCheckByMember(RequestStartClimbMountainQueryDTO request);
}
