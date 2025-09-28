package com.bughunters.mountainspirit.crewclimbboard.command.service;

import com.bughunters.mountainspirit.crewclimbboard.command.dto.CrewClimbBoardDTO;
import com.bughunters.mountainspirit.crewclimbboard.command.dto.CrewClimbRecordRegistDTO;
import com.bughunters.mountainspirit.crewclimbboard.command.entity.CrewClimbBoard;
import com.bughunters.mountainspirit.crewclimbboard.command.entity.CrewClimbRecord;
import com.bughunters.mountainspirit.crewclimbboard.command.repository.CrewClimbBoardCommendRepository;
import com.bughunters.mountainspirit.crewclimbboard.command.repository.CrewClimbRecordCommendRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CrewClimbBoardCommendServiceImpl implements CrewClimbBoardCommendService {
    ModelMapper modelMapper;
    private CrewClimbBoardCommendRepository crewClimbBoardCommendRepository;
    private CrewClimbRecordCommendRepository crewClimbRecordCommendRepository;

    @Autowired
    public CrewClimbBoardCommendServiceImpl(CrewClimbBoardCommendRepository crewClimbBoardCommendRepository,
                                            ModelMapper modelMapper,
                                            CrewClimbRecordCommendRepository crewClimbRecordCommendRepository) {
        this.crewClimbBoardCommendRepository = crewClimbBoardCommendRepository;
        this.modelMapper = modelMapper;
        this.crewClimbRecordCommendRepository = crewClimbRecordCommendRepository;
    }

    @Override
    @Transactional
    public void registCrewClimbBoard(CrewClimbBoardDTO crewClimbBoardDTO) {
        // 넘어온 DTO값 맵핑 + default값 세팅
        CrewClimbBoard crewClimbBoard = modelMapper.map(crewClimbBoardDTO, CrewClimbBoard.class);
        log.info("Service 크루 모집 일정 넘어온거 맵핑 값 : {}", crewClimbBoard);
        crewClimbBoard.setCrewClimbIsDeleted('N');
        crewClimbBoard.setCrewClimbIsEnded('N');

        // 등산 모집 일정 insert
        crewClimbBoard = crewClimbBoardCommendRepository.save(crewClimbBoard);
        log.info("등산일정 : {}",crewClimbBoard);

        // 등산 기록 저장을 위한 DTO 맵핑
        CrewClimbRecord crewClimbRecord = new CrewClimbRecord();
        crewClimbRecord.setCrewClimbId(crewClimbBoard.getId());
        crewClimbRecord.setCrewMemberId(crewClimbBoard.getCrewMemberId());
        crewClimbRecord.setFrtrlId(crewClimbBoard.getFrtrlId());

        // 등산 모집 작성자 크루 등산 기록 insert
        crewClimbRecordCommendRepository.save(crewClimbRecord);

        // 추후 일정 중복 등록 처리 요망
    }

    @Override
    @Transactional
    public void modifyCrewClimbBoard(CrewClimbBoardDTO crewClimbBoardDTO) {
        // 크루 등산 일정 맵핑
        CrewClimbBoard crewClimbBoard = modelMapper.map(crewClimbBoardDTO, CrewClimbBoard.class);
        log.info("Service 크루 모집 수정 넘어온거 맵핑 값 : {}", crewClimbBoard);

        // 크루 등산 일정 원본
        CrewClimbBoard originalCrewClimbBoard = crewClimbBoardCommendRepository.findById(crewClimbBoardDTO.getId()).orElse(null);
        log.info("Service 크루 모집 수정 할 원본 Entity : {}", originalCrewClimbBoard);

        // 만약 크루 등산 일정 원본이 없으면
        if (originalCrewClimbBoard == null) {
            log.info("없는 데이터에 접근하였습니다.");
            return;
        }

        // 크루 등산 일정 수정 수행
        compareAndModifyCrewClimbBoard(crewClimbBoard, originalCrewClimbBoard);
    }

    @Override
    @Transactional
    public void deleteCrewClimbBoardById(Long id) {
        CrewClimbBoard crewClimbBoard = crewClimbBoardCommendRepository.findById(id).orElse(null);
        if (crewClimbBoard == null) {
            log.info("없는 데이터에 접근하였습니다.");
            return;
        }
        crewClimbBoard.setCrewClimbIsDeleted('Y');
        crewClimbRecordCommendRepository.deleteById(id);

    }

    @Override
    @Transactional
    public void closeCrewClimbBoardApplyById(Long id) {
        CrewClimbBoard crewClimbBoard = crewClimbBoardCommendRepository.findById(id).orElse(null);
        if (crewClimbBoard == null) {
            log.info("없는 데이터에 접근하였습니다.");
            return;
        }
        crewClimbBoard.setCrewClimbIsEnded('Y');
    }



    // ////////////////////////////////////////////
    private void compareAndModifyCrewClimbBoard(CrewClimbBoard crewClimbBoard, CrewClimbBoard originalCrewClimbBoard) {
        if (!crewClimbBoard.getCrewClimbStartDate().equals(originalCrewClimbBoard.getCrewClimbStartDate())) {
            originalCrewClimbBoard.setCrewClimbStartDate(crewClimbBoard.getCrewClimbStartDate());
        }
        if (!crewClimbBoard.getCrewClimbRecruitStartDate().equals(originalCrewClimbBoard.getCrewClimbRecruitStartDate())) {
            originalCrewClimbBoard.setCrewClimbRecruitStartDate(crewClimbBoard.getCrewClimbRecruitStartDate());
        }
        if (!crewClimbBoard.getCrewClimbRecruitEndDate().equals(originalCrewClimbBoard.getCrewClimbRecruitEndDate())) {
            originalCrewClimbBoard.setCrewClimbRecruitEndDate(crewClimbBoard.getCrewClimbRecruitEndDate());
        }
        if (!crewClimbBoard.getCrewClimbContent().equals(originalCrewClimbBoard.getCrewClimbContent())) {
            originalCrewClimbBoard.setCrewClimbContent(crewClimbBoard.getCrewClimbContent());
        }
        if (crewClimbBoard.getCrewClimbAmountOfPeople().equals(originalCrewClimbBoard.getCrewClimbAmountOfPeople())) {
            originalCrewClimbBoard.setCrewClimbAmountOfPeople(crewClimbBoard.getCrewClimbAmountOfPeople());
        }
    }
}
