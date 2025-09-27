package com.bughunters.mountainspirit.climbhistory.query.service;

import com.bughunters.mountainspirit.climbhistory.query.dto.FindClimbCheckQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.dto.RequestStartClimbMountainQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.mapper.ClimbHistoryQueryRepository;
import org.springframework.stereotype.Service;

@Service
public class ClimbHistoryQueryServiceImpl implements ClimbHistoryQueryService {

    private final ClimbHistoryQueryRepository climbHistoryQueryRepository;

    public ClimbHistoryQueryServiceImpl(ClimbHistoryQueryRepository climbHistoryQueryRepository) {
        this.climbHistoryQueryRepository = climbHistoryQueryRepository;
    }

    @Override
    public FindClimbCheckQueryDTO findClimbCheckByMember(RequestStartClimbMountainQueryDTO request) {

        FindClimbCheckQueryDTO findClimbCheckQueryDTO =
                climbHistoryQueryRepository.selectClimbCheckByMember(request);

        return findClimbCheckQueryDTO;
    }
}
