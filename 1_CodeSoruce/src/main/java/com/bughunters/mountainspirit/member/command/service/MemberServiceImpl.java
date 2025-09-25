package com.bughunters.mountainspirit.member.command.service;

import com.bughunters.mountainspirit.member.command.dto.FindClimbCheckDTO;
import com.bughunters.mountainspirit.member.command.dto.RequestStartClimbMountainDTO;
import com.bughunters.mountainspirit.member.command.dto.ResponseClimbMountainDTO;
import com.bughunters.mountainspirit.member.command.entity.ClimbCheck;
import com.bughunters.mountainspirit.member.command.repository.ClimbCheckRepository;
import com.bughunters.mountainspirit.member.query.dto.FindClimbCheckQueryDTO;
import com.bughunters.mountainspirit.member.query.dto.RequestStartClimbMountainQueryDTO;
import com.bughunters.mountainspirit.member.query.service.MemberQueryServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberQueryServiceImpl memberQueryServiceImpl;
    private final ModelMapper modelMapper;
    private final ClimbCheckRepository climbCheckRepository;

    public MemberServiceImpl(MemberQueryServiceImpl memberQueryServiceImpl
            , ModelMapper modelMapper
            , ClimbCheckRepository climbCheckRepository) {
        this.memberQueryServiceImpl = memberQueryServiceImpl;
        this.modelMapper = modelMapper;
        this.climbCheckRepository = climbCheckRepository;
    }

    @Override
    @Transactional
    public FindClimbCheckDTO startClimbMountain(RequestStartClimbMountainDTO request) {

        RequestStartClimbMountainQueryDTO requestStartClimbMountainQueryDTO =
                modelMapper.map(request, RequestStartClimbMountainQueryDTO.class);

        FindClimbCheckQueryDTO findClimbCheckQueryDTO
                = memberQueryServiceImpl.findClimbCheckByMember(requestStartClimbMountainQueryDTO);
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
