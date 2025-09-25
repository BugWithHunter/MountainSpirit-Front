package com.bughunters.mountainspirit.climbhistory.command.service;

import com.bughunters.mountainspirit.climbhistory.command.dto.FindClimbCheckDTO;
import com.bughunters.mountainspirit.climbhistory.command.dto.RequestStartClimbMountainDTO;
import com.bughunters.mountainspirit.climbhistory.command.entity.ClimbCheck;
import com.bughunters.mountainspirit.climbhistory.command.repository.ClimbCheckRepository;
import com.bughunters.mountainspirit.climbhistory.query.dto.FindClimbCheckQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.dto.RequestStartClimbMountainQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.service.ClimbHistoryQueryServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClimbHistoryServiceImpl implements ClimbHistoryService {

    private final ClimbHistoryQueryServiceImpl climbHistoryQueryServiceImpl;
    private final ModelMapper modelMapper;
    private final ClimbCheckRepository climbCheckRepository;

    public ClimbHistoryServiceImpl(ClimbHistoryQueryServiceImpl climbHistoryQueryServiceImpl
            , ModelMapper modelMapper
            , ClimbCheckRepository climbCheckRepository) {
        this.climbHistoryQueryServiceImpl = climbHistoryQueryServiceImpl;
        this.modelMapper = modelMapper;
        this.climbCheckRepository = climbCheckRepository;
    }

    @Override
    @Transactional
    public FindClimbCheckDTO startClimbMountain(RequestStartClimbMountainDTO request) {

        RequestStartClimbMountainQueryDTO requestStartClimbMountainQueryDTO =
                modelMapper.map(request, RequestStartClimbMountainQueryDTO.class);

        FindClimbCheckQueryDTO findClimbCheckQueryDTO
                = climbHistoryQueryServiceImpl.findClimbCheckByMember(requestStartClimbMountainQueryDTO);
        FindClimbCheckDTO findClimbCheckDTO = null;

        //메모. null 이면 등산 시작 데이터 insert
        if (findClimbCheckQueryDTO == null) {
            ClimbCheck climbCheck = modelMapper.map(request, ClimbCheck.class);
            climbCheckRepository.save(climbCheck);
        } else {
            findClimbCheckDTO = modelMapper.map(findClimbCheckQueryDTO, FindClimbCheckDTO.class);
        }

        return findClimbCheckDTO;
    }
}
