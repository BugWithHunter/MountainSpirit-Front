package com.bughunters.mountainspirit.crewclimbboard.command.service;

import com.bughunters.mountainspirit.crewclimbboard.command.dto.CrewClimbBoardApplyDTO;
import com.bughunters.mountainspirit.crewclimbboard.command.dto.CrewClimbBoardCancelDTO;
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

import java.util.List;

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
        log.info("등산일정 : {}", crewClimbBoard);

        // 등산 기록 저장을 위한 DTO 맵핑
        CrewClimbRecord crewClimbRecord = new CrewClimbRecord();
//        crewClimbRecord.setCrewClimbHistoryIsSucceed('N');
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
        // 삭제 작업 시 등산 기록에 있는 정보들도 transactional 하게 삭제되게 구현
        CrewClimbBoard crewClimbBoard = crewClimbBoardCommendRepository.findById(id).orElse(null);
        if (crewClimbBoard == null) {
            log.info("없는 데이터에 접근하였습니다.");
            return;
        }
        crewClimbBoard.setCrewClimbIsDeleted('Y');
        crewClimbRecordCommendRepository.deleteAllByCrewClimbId(id);

    }

    @Override
    @Transactional
    public void closeCrewClimbBoardById(Long id) {
        CrewClimbBoard crewClimbBoard = crewClimbBoardCommendRepository.findById(id).orElse(null);
        if (crewClimbBoard == null) {
            log.info("없는 데이터에 접근하였습니다.");
            return;
        }
        crewClimbBoard.setCrewClimbIsEnded('Y');
    }

    @Override
    @Transactional
    public void applyCrewClimbBoard(CrewClimbBoardApplyDTO crewClimbBoardApplyDTO) {
        int climbMemberCnt = crewClimbRecordCommendRepository.countByCrewClimbId(crewClimbBoardApplyDTO.getCrewClimbId());

        // 등산 인원이 차있을 시 등산 신청 불가
        if (climbMemberCnt >= crewClimbBoardApplyDTO.getCrewClimbAmountOfPeople()) {
            log.info("크루 인원이 가득 차 있습니다.");
            return;
        }

        CrewClimbRecord crewClimbRecord = setCrewClimbRecordRegistInfo(crewClimbBoardApplyDTO);
        crewClimbRecordCommendRepository.save(crewClimbRecord);

        // 등산 인원이 가득 차 있지 않으면 메소드 종료
        if (climbMemberCnt + 1 < crewClimbBoardApplyDTO.getCrewClimbAmountOfPeople()) return;

        // 등산 인원이 가득 찼을 시 자동으로 모집 마감
        CrewClimbBoard crewClimbBoard = crewClimbBoardCommendRepository.findById(crewClimbBoardApplyDTO.getCrewClimbId()).orElse(null);
        if (crewClimbBoard == null) {
            log.info("없는 데이터에 접근하였습니다.");
            return;
        }
        crewClimbBoard.setCrewClimbIsEnded('Y');

    }

    @Override
    @Transactional
    public void cancelCrewClimb(CrewClimbBoardCancelDTO crewClimbBoardCancelDTO) {
        CrewClimbRecord crewClimbRecord = crewClimbRecordCommendRepository.findByCrewClimbIdAndCrewMemberId(crewClimbBoardCancelDTO.getCrewClimbId(),crewClimbBoardCancelDTO.getCrewMemberId());
        if(crewClimbRecord==null){
            log.info("없는 데이터에 접근하였습니다.");
            return;
        }
        crewClimbRecordCommendRepository.delete(crewClimbRecord);
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

    private static CrewClimbRecord setCrewClimbRecordRegistInfo(CrewClimbBoardApplyDTO crewClimbBoardApplyDTO) {
        CrewClimbRecord crewClimbRecord = new CrewClimbRecord();
//        crewClimbRecord.setCrewClimbHistoryIsSucceed('N');
        crewClimbRecord.setCrewClimbId(crewClimbBoardApplyDTO.getCrewClimbId());
        crewClimbRecord.setCrewMemberId(crewClimbBoardApplyDTO.getCrewMemberId());
        crewClimbRecord.setFrtrlId(crewClimbBoardApplyDTO.getFrtrlId());
        return crewClimbRecord;
    }
}
