package com.bughunters.mountainspirit.climbhistory.query.service;

import com.bughunters.mountainspirit.climbhistory.query.dto.FindClimbCheckQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.dto.FindNotCompleteClimbCheckQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.dto.RequestStartClimbMountainQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.dto.SelectClimbingRecordMonthlyDTO;
import com.bughunters.mountainspirit.climbhistory.query.mapper.ClimbHistoryQueryRepository;
import com.bughunters.mountainspirit.climbhistory.query.mapper.ClimbRecordQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClimbHistoryQueryServiceImpl implements ClimbHistoryQueryService {

    private final ClimbHistoryQueryRepository climbHistoryQueryRepository;
    private final ClimbRecordQueryRepository climbRecordQueryRepository;

    @Autowired
    public ClimbHistoryQueryServiceImpl(ClimbHistoryQueryRepository climbHistoryQueryRepository,
                                        ClimbRecordQueryRepository climbRecordQueryRepository) {
        this.climbHistoryQueryRepository = climbHistoryQueryRepository;
        this.climbRecordQueryRepository = climbRecordQueryRepository;
    }

    @Override
    public FindClimbCheckQueryDTO findClimbCheckByMember(RequestStartClimbMountainQueryDTO request) {

        FindClimbCheckQueryDTO findClimbCheckQueryDTO =
                climbHistoryQueryRepository.selectClimbCheckByMember(request);

        return findClimbCheckQueryDTO;
    }

    @Override
    public List<FindNotCompleteClimbCheckQueryDTO> findClimbNotComplete(RequestStartClimbMountainQueryDTO request) {

        List<FindNotCompleteClimbCheckQueryDTO> notCompleteClimbs =
                climbHistoryQueryRepository.selectAllClimbCheckByMember(request);

        return notCompleteClimbs;
    }

    @Override
    public List<SelectClimbingRecordMonthlyDTO> selectMonthlyRecord(Long id) {
        List<SelectClimbingRecordMonthlyDTO> selectClimbingRecordMonthlyDTO
                = climbRecordQueryRepository.SelectClimbingRecordMonthly(id);

        List<String> months = selectClimbingRecordMonthlyDTO.stream()
                .map(SelectClimbingRecordMonthlyDTO::getMonthly).toList();

        List<Integer> counts = selectClimbingRecordMonthlyDTO.stream()
                .map(SelectClimbingRecordMonthlyDTO::getCount).toList();

        return selectClimbingRecordMonthlyDTO;

    }


}
