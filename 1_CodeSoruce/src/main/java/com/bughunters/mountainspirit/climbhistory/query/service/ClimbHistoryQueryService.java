package com.bughunters.mountainspirit.climbhistory.query.service;


import com.bughunters.mountainspirit.climbhistory.query.dto.FindClimbCheckQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.dto.RequestStartClimbMountainQueryDTO;

public interface ClimbHistoryQueryService {
    FindClimbCheckQueryDTO findClimbCheckByMember(RequestStartClimbMountainQueryDTO request);
}
