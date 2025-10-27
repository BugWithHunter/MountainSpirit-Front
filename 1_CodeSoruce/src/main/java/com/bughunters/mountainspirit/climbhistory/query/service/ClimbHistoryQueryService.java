package com.bughunters.mountainspirit.climbhistory.query.service;


import com.bughunters.mountainspirit.climbhistory.query.dto.FindClimbCheckQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.dto.FindNotCompleteClimbCheckQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.dto.RequestStartClimbMountainQueryDTO;

import java.util.List;

public interface ClimbHistoryQueryService {
    FindClimbCheckQueryDTO findClimbCheckByMember(RequestStartClimbMountainQueryDTO request);

    List<FindNotCompleteClimbCheckQueryDTO> findClimbNotComplete(RequestStartClimbMountainQueryDTO request);
}
