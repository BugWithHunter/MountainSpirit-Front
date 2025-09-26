package com.bughunters.mountainspirit.climbhistory.command.service;

import com.bughunters.mountainspirit.climbhistory.command.dto.FindClimbCheckDTO;
import com.bughunters.mountainspirit.climbhistory.command.dto.RequestSubmmitClimbMountainDTO;
import com.bughunters.mountainspirit.climbhistory.command.entity.ClimbCheck;
import com.bughunters.mountainspirit.climbhistory.command.entity.ClimbRecord;
import com.bughunters.mountainspirit.climbhistory.command.repository.ClimbCheckRepository;
import com.bughunters.mountainspirit.climbhistory.command.repository.ClimbRecordRepository;
import com.bughunters.mountainspirit.climbhistory.query.dto.FindClimbCheckQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.dto.RequestStartClimbMountainQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.service.ClimbHistoryQueryServiceImpl;
import com.bughunters.mountainspirit.stamp.command.dto.StampWithCourseAndMountainDTO;
import com.bughunters.mountainspirit.stamp.command.service.StampService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ClimbHistoryServiceImpl implements ClimbHistoryService {

    private final ClimbHistoryQueryServiceImpl climbHistoryQueryServiceImpl;
    private final ModelMapper modelMapper;
    private final ClimbCheckRepository climbCheckRepository;
    private final ClimbRecordRepository climbRecordRepository;
    private final StampService stampService;

    public ClimbHistoryServiceImpl(ClimbHistoryQueryServiceImpl climbHistoryQueryServiceImpl
            , ModelMapper modelMapper
            , ClimbCheckRepository climbCheckRepository
            , ClimbRecordRepository climbRecordRepository
            , StampService stampService) {
        this.climbHistoryQueryServiceImpl = climbHistoryQueryServiceImpl;
        this.modelMapper = modelMapper;
        this.climbCheckRepository = climbCheckRepository;
        this.climbRecordRepository = climbRecordRepository;
        this.stampService = stampService;
    }

    @Override
    @Transactional
    public FindClimbCheckDTO startClimbMountain(RequestSubmmitClimbMountainDTO request) {

        FindClimbCheckQueryDTO findClimbCheckQueryDTO = getFindClimbCheckQueryDTO(request);

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


    @Override
    @Transactional
    public FindClimbCheckDTO completeClimbMountain(RequestSubmmitClimbMountainDTO request) {

        FindClimbCheckQueryDTO findClimbCheckQueryDTO = getFindClimbCheckQueryDTO(request);

        FindClimbCheckDTO findClimbCheckDTO = null;

        //메모. null이 아니면 등산 완료 처리 및 등산기록 insert, 회원 테이블에 등산 횟수 업데이트
        if (findClimbCheckQueryDTO != null) {
            LocalDateTime completeTime = LocalDateTime.now();
            //메모. 1. 등산 완료 인증 업데이트
            updateClimbCheck(request, findClimbCheckQueryDTO, completeTime);

            //메모. 2.등산 기록 테이블 insert
            insertClimbRecod(request, findClimbCheckQueryDTO, completeTime);

            //메모. 3.코스도장 희득 조건 확인 후 도장 흭득(흭득 유무 boolean 반환 점수 반영을 위함)

            com.bughunters.mountainspirit.stamp.command.dto.RequestSubmmitClimbMountainDTO requestDTO
                    = modelMapper.map(request,com.bughunters.mountainspirit.stamp.command.dto.RequestSubmmitClimbMountainDTO.class);

            StampWithCourseAndMountainDTO stampDTO = stampService.copleteClimbingMountain(requestDTO);
            //메모. 4.산도장 흭득 조건 확인 후 도장 흭득(흭득 유무 boolean 반환 점수 반영을 위함)

            //메모. 5.점수 집계

            //메모. 7.등급 기준 체크, 등급 업 할 등급 코드 반환

            //메모. 8.산마스터 등급 기준 체크, 기준 등급 반환

            //메모. 9.산신령 등급으로 올라 갈 경우 기존 해당 산의 산신령 등급 변경

            //메모. 10.회원 테이블 등산 횟수 , 점수 ,등급 update


            //메모. 11.반환 할 데이터
            findClimbCheckDTO = modelMapper.map(findClimbCheckQueryDTO, FindClimbCheckDTO.class);
            findClimbCheckDTO.setStateCode("Y");
            findClimbCheckDTO.setEndTime(completeTime);
        } else {
        }

        return findClimbCheckDTO;
    }

    private void insertClimbRecod(RequestSubmmitClimbMountainDTO request, FindClimbCheckQueryDTO findClimbCheckQueryDTO, LocalDateTime completeTime) {
        ClimbRecord climbRecord = modelMapper.map(request, ClimbRecord.class);
        climbRecord.setStartTime(findClimbCheckQueryDTO.getUpdateTime());
        climbRecord.setEndTime(completeTime);
        climbRecordRepository.save(climbRecord);
    }

    private void updateClimbCheck(RequestSubmmitClimbMountainDTO request, FindClimbCheckQueryDTO findClimbCheckQueryDTO, LocalDateTime completeTime) {
        ClimbCheck climbCheck = modelMapper.map(request, ClimbCheck.class);
        climbCheck.setId(findClimbCheckQueryDTO.getId());
        climbCheck.setStateCode("Y");
        climbCheck.setEndTime(completeTime);
        climbCheckRepository.save(climbCheck);
    }


    private FindClimbCheckQueryDTO getFindClimbCheckQueryDTO(RequestSubmmitClimbMountainDTO request) {
        RequestStartClimbMountainQueryDTO requestStartClimbMountainQueryDTO =
                modelMapper.map(request, RequestStartClimbMountainQueryDTO.class);

        FindClimbCheckQueryDTO findClimbCheckQueryDTO
                = climbHistoryQueryServiceImpl.findClimbCheckByMember(requestStartClimbMountainQueryDTO);
        return findClimbCheckQueryDTO;
    }
}
