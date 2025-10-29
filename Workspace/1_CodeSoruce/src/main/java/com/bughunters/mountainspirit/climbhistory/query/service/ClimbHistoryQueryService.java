package com.bughunters.mountainspirit.climbhistory.query.service;


import com.bughunters.mountainspirit.climbhistory.query.dto.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public interface ClimbHistoryQueryService {
    FindClimbCheckQueryDTO findClimbCheckByMember(RequestStartClimbMountainQueryDTO request);

    List<FindNotCompleteClimbCheckQueryDTO> findClimbNotComplete(RequestStartClimbMountainQueryDTO request);

    ResponseRecordMonthlyDTO selectMonthlyRecord(Long id);

    Map<String, SpiritProfileDTO> findMountainRank(HttpServletRequest request);
}
