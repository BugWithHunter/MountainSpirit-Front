package com.bughunters.mountainspirit.crewmember.command.service;

import com.bughunters.mountainspirit.crewmember.command.dto.CrewApplyDTO;
import com.bughunters.mountainspirit.crewmember.command.entity.*;
import com.bughunters.mountainspirit.crewmember.command.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class CrewMemberCommendServiceImpl implements CrewMemberCommendService {
    private final ModelMapper modelMapper;
    CrewMemberCommendRepository crewMemberCommendRepository;
    CrewMemberHistoryCommendRepository crewMemberHistoryCommendRepository;
    CrewApplyCommendRepository crewApplyCommendRepository;
    CrewApplyHistoryCommendRepository crewApplyHistoryCommendRepository;
    MemberCommendRepository memberCommendRepository;

    @Autowired
    public CrewMemberCommendServiceImpl(ModelMapper modelMapper,
                                        CrewMemberCommendRepository crewMemberCommendRepository,
                                        CrewMemberHistoryCommendRepository crewMemberHistoryCommendRepository,
                                        CrewApplyCommendRepository crewApplyCommendRepository,
                                        CrewApplyHistoryCommendRepository crewApplyHistoryCommendRepository,
                                        MemberCommendRepository memberCommendRepository) {
        this.modelMapper = modelMapper;
        this.crewMemberCommendRepository = crewMemberCommendRepository;
        this.crewMemberHistoryCommendRepository = crewMemberHistoryCommendRepository;
        this.crewApplyCommendRepository = crewApplyCommendRepository;
        this.crewApplyHistoryCommendRepository = crewApplyHistoryCommendRepository;
        this.memberCommendRepository = memberCommendRepository;
    }

    @Override
    @Transactional
    public void crewApplyRequest(CrewApplyDTO crewApplyDTO) {
        // 이미 가입 신청이 되어있을 시 가입 신청 불가
        if(crewApplyCommendRepository.existsByCrewIdAndCumId(crewApplyDTO.getCrewId(),crewApplyDTO.getCumId())) {
            log.info("이미 크루 가입 신청이 되어 있습니다.");
            return;
        }


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
        // 크루 가입 신청 정보 검색
        CrewApply crewApply = crewApplyCommendRepository.findByCrewIdAndCumId(crewApplyDTO.getCrewId(),crewApplyDTO.getCumId());

        // 크루 가입 신청 삭제
        crewApplyCommendRepository.delete(crewApply);
    }

    @Override
    @Transactional
    public void registCrewMemberByCrewApplyApprove(CrewApplyDTO crewApplyDTO) {
        // 넘어온 데이터에 값이 없으면 리턴
        if(crewApplyDTO.getCrewId()==null||crewApplyDTO.getCumId()==null)return;

        CrewApply crewApply = crewApplyCommendRepository.findByCrewIdAndCumId(crewApplyDTO.getCrewId(),crewApplyDTO.getCumId());
        log.info("service crewApply 값 : {}",crewApply.toString());

        crewApplyDTO.setId(crewApply.getId());
        crewApplyDTO.setCrewApplyDate(crewApply.getCrewApplyDate());

        // 크루구성원(CrewMember) 테이블에 크루구성원 데이터 insert
        CrewMember crewMember = setCrewMemberInfo(crewApplyDTO);
        crewMemberCommendRepository.save(crewMember);

        // 크루 가입 신청 히스토리(CrewApplyHistory)에 데이터 insert
        CrewApplyHistory crewApplyHistory = setCrewApplyHistoryInfo(crewApplyDTO,'Y');
        crewApplyHistoryCommendRepository.save(crewApplyHistory);

        // 크루 구성원 히스토리(CrewMemberHistory) 테이블에서 crewMemberHistoryState를 JOINED로 설정하고 insert
        CrewMemberHistory crewMemberHistory = setCrewMemberHistoryInfo(crewApplyDTO,1L);
        crewMemberHistoryCommendRepository.save(crewMemberHistory);

        // 회원(Member) 테이블에 크루 정보 수정
        Member member = memberCommendRepository.findById(crewApplyDTO.getCumId()).get();
        member.setCrewId(crewApplyDTO.getCrewId());

        // 크루 가입 신청(CrewApply) 테이블에서 신청 데이터 delete
        crewApplyCommendRepository.delete(crewApply);

        // 후에 DB에 쿼리문 여러번 날아가지 않게 ToString 수정을 하든지 해야 될듯하다, 테스트 케이스로 개선사항 작성하면 더 좋을듯하다.
    }

    @Override
    @Transactional
    public void crewApplyRejected(CrewApplyDTO crewApplyDTO) {
        // 크루 가입 신청 정보 검색
        CrewApply crewApply = crewApplyCommendRepository.findByCrewIdAndCumId(crewApplyDTO.getCrewId(),crewApplyDTO.getCumId());
        crewApplyDTO.setCrewApplyDate(crewApply.getCrewApplyDate());

        // 크루 가입 신청 히스토리 데이터 insert
        CrewApplyHistory crewApplyHistory = setCrewApplyHistoryInfo(crewApplyDTO,'N');
        crewApplyHistoryCommendRepository.save(crewApplyHistory);

        // 크루 가입 신청 데이터 delete
        crewApplyCommendRepository.delete(crewApply);
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

    private CrewMemberHistory setCrewMemberHistoryInfo(CrewApplyDTO crewApplyDTO,Long crewMemberRole) {
        CrewMemberHistory crewMemberHistory = new CrewMemberHistory();
        LocalDateTime now = LocalDateTime.now();

        crewMemberHistory.setCrewRoleId(crewMemberRole);
        crewMemberHistory.setCrewId(crewApplyDTO.getCrewId());
        crewMemberHistory.setCrewMemberHistoryJoinDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewMemberHistory.setCrewMemberHistoryStateUpdateDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewMemberHistory.setCrewMemberHistoryState("JOINED");
        crewMemberHistory.setCrewMemberHistoryUpdateReason("크루 가입");
        crewMemberHistory.setCumId(crewApplyDTO.getCumId());
        return crewMemberHistory;
    }

    private CrewApplyHistory setCrewApplyHistoryInfo(CrewApplyDTO crewApplyDTO, char isAccepted) {
        CrewApplyHistory crewApplyHistory = new CrewApplyHistory();
        LocalDateTime now = LocalDateTime.now();

        crewApplyHistory.setCrewId(crewApplyDTO.getCrewId());
        crewApplyHistory.setCrewApplyHistoryDate(crewApplyDTO.getCrewApplyDate());
        crewApplyHistory.setCrewApplyHistoryIsAccepted(isAccepted);
        crewApplyHistory.setCrewApplyHistoryAcceptDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewApplyHistory.setCumId(crewApplyDTO.getCumId());
        return crewApplyHistory;
    }



}
