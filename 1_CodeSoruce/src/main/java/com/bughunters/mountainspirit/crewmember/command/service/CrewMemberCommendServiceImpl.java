package com.bughunters.mountainspirit.crewmember.command.service;

import com.bughunters.mountainspirit.crewmember.command.dto.CrewApplyDTO;
import com.bughunters.mountainspirit.crewmember.command.entity.CrewApply;
import com.bughunters.mountainspirit.crewmember.command.entity.CrewMember;
import com.bughunters.mountainspirit.crewmember.command.repository.CrewApplyCommendRepository;
import com.bughunters.mountainspirit.crewmember.command.repository.CrewApplyHistoryCommendRepository;
import com.bughunters.mountainspirit.crewmember.command.repository.CrewMemberCommendRepository;
import com.bughunters.mountainspirit.crewmember.command.repository.CrewMemberHistoryCommendRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class CrewMemberCommendServiceImpl implements CrewMemberCommendService {
    private final ModelMapper modelMapper;
    CrewMemberCommendRepository crewMemberCommendRepository;
    CrewMemberHistoryCommendRepository crewMemberHistoryCommendRepository;
    CrewApplyCommendRepository crewApplyCommendRepository;
    CrewApplyHistoryCommendRepository crewApplyHistoryCommendRepository;

    @Autowired
    public CrewMemberCommendServiceImpl(ModelMapper modelMapper,
                                        CrewMemberCommendRepository crewMemberCommendRepository,
                                        CrewMemberHistoryCommendRepository crewMemberHistoryCommendRepository,
                                        CrewApplyCommendRepository crewApplyCommendRepository,
                                        CrewApplyHistoryCommendRepository crewApplyHistoryCommendRepository) {
        this.modelMapper = modelMapper;
        this.crewMemberCommendRepository = crewMemberCommendRepository;
        this.crewMemberHistoryCommendRepository = crewMemberHistoryCommendRepository;
        this.crewApplyCommendRepository = crewApplyCommendRepository;
        this.crewApplyHistoryCommendRepository = crewApplyHistoryCommendRepository;
    }

    @Override
    @Transactional
    public void crewApplyRequest(CrewApplyDTO crewApplyDTO) {
        // ///////이미 가입 신청이 되어있을 시 가입 신청 불가 구현 요망///////


        // ///////////////////////////////////////////////////////////
        CrewApply crewApply = new CrewApply();
        LocalDateTime now = LocalDateTime.now();
        crewApply.setCrewApplyDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewApply.setCrewId(crewApplyDTO.getCrewId());
        crewApply.setCumId(crewApplyDTO.getCumId());

        crewApplyCommendRepository.save(crewApply);
    }

    @Override
    @Transactional
    public void crewApplyCancel(CrewApplyDTO crewApplyDTO) {
        // 크루 신청 정보 검색
        CrewApply crewApply = crewApplyCommendRepository.findByCrewIdAndCumId(crewApplyDTO.getCrewId(),crewApplyDTO.getCumId());

        // 크루 가입 신청 삭제
        crewApplyCommendRepository.delete(crewApply);

    }

    @Override
    @Transactional
    public void registCrewMemberByCrewApplyApprove(CrewApplyDTO crewApplyDTO) {
        // CrewMember 테이블에 크루 회원 insert
        CrewMember crewMember = setCrewMemberInfo(crewApplyDTO);
        crewMemberCommendRepository.save(crewMember);

        // 크루 가입 신청 히스토리(CrewApplyHistory)에 데이터 insert
        

        // 크루 구성원 히스토리(CrewMemberHistory) 테이블에서 crewMemberHistoryState를 JOINED로 설정하고 insert

        // 회원(Member) 테이블에 크루 정보 수정

        // 크루 가입 신청(CrewApply) 테이블에서 신청 데이터 delete

    }



    // //////////////////////////////////////////////
    private CrewMember setCrewMemberInfo(CrewApplyDTO crewApplyDTO) {
        LocalDateTime now = LocalDateTime.now();
        CrewMember crewMember = new CrewMember();
        crewMember.setCrewMemberJoinDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewMember.setCrewMemberRoleUpdateDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewMember.setCrewId(crewApplyDTO.getCrewId());
        crewMember.setCumId(crewApplyDTO.getCumId());
        crewMember.setCrewRoleId(1L);
        return crewMember;
    }

}
