package com.bughunters.mountainspirit.crew.command.service;


import com.bughunters.mountainspirit.crew.command.dto.CrewDeleteDTO;
import com.bughunters.mountainspirit.crew.command.dto.CrewModifyDTO;
import com.bughunters.mountainspirit.crew.command.dto.CrewRegistDTO;
import com.bughunters.mountainspirit.crew.command.entity.Crew;
import com.bughunters.mountainspirit.crew.command.repository.CrewCommendRepository;
import com.bughunters.mountainspirit.crewmember.command.dto.CrewApplyDTO;
import com.bughunters.mountainspirit.crewmember.command.entity.CrewApplyHistory;
import com.bughunters.mountainspirit.crewmember.command.entity.CrewMember;
import com.bughunters.mountainspirit.crewmember.command.entity.CrewMemberHistory;
import com.bughunters.mountainspirit.crewmember.command.infrastructure.MemberServiceClient;
import com.bughunters.mountainspirit.crewmember.command.repository.CrewMemberCommendRepository;
import com.bughunters.mountainspirit.crewmember.command.repository.CrewMemberHistoryCommendRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class CrewCommendServiceImpl implements CrewCommendService {
    private final CrewCommendRepository crewCommendRepository;
    CrewMemberCommendRepository crewMemberCommendRepository;
    CrewMemberHistoryCommendRepository crewMemberHistoryCommendRepository;
    MemberServiceClient memberServiceClient;
    ModelMapper modelMapper;

    @Autowired
    public CrewCommendServiceImpl(CrewCommendRepository crewCommendRepository,
                                  CrewMemberCommendRepository crewMemberCommendRepository,
                                  CrewMemberHistoryCommendRepository crewMemberHistoryCommendRepository,
                                  MemberServiceClient memberServiceClient,
                                  ModelMapper modelMapper) {
        this.crewCommendRepository = crewCommendRepository;
        this.crewMemberCommendRepository = crewMemberCommendRepository;
        this.crewMemberHistoryCommendRepository = crewMemberHistoryCommendRepository;
        this.memberServiceClient = memberServiceClient;
        this.modelMapper = modelMapper;
    }

    @Override
    public void insertCrew(CrewRegistDTO crewRegistDTO) {
        Long crewId = null;
        try {
            crewId = insertCrewTransaction(crewRegistDTO);
            log.info("CrewMember insert 완료, CrewId: {}",crewId);

            // Member 테이블에 Crew 정보 저장
            memberServiceClient.updateMemberCrewInfo(crewId, crewRegistDTO.getCumId());
        } catch (Exception e) {

        }
    }

    @Transactional
    public Long insertCrewTransaction(CrewRegistDTO crewRegistDTO) {
        Crew crew = null;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        // 기본값 세팅
        crew = setDefaultCrewValues(modelMapper.map(crewRegistDTO, Crew.class));

        // Crew 테이블에 크루 정보 저장
        crewCommendRepository.save(crew);
        log.info("crewEntity : {}", crew);

        // CrewMember 테이블에 크루장 정보 저장
        CrewMember crewMember = setCrewMemberInfo(crew.getId(), crewRegistDTO.getCumId());
        crewMemberCommendRepository.save(crewMember);
        log.info("service crewMember 값 : {}", crewMember);

        // CrewMemberHistory 테이블에 정보 저장
        CrewMemberHistory crewMemberHistory = setCrewMemberHistoryInfo(crew.getId(), crewRegistDTO.getCumId(), 2L);
        log.info("service CrewMemberHistory 값 : {}", crewMemberHistory);
        crewMemberHistoryCommendRepository.save(crewMemberHistory);
        return crew.getId();
    }

    @Override
    @Transactional
    public void modifyCrew(CrewModifyDTO crewModifyDTO) {
        Crew crew = crewCommendRepository.findById(crewModifyDTO.getId()).get();
        modifyCrewEntity(crewModifyDTO, crew);
    }

    @Override
    @Transactional
    public void deleteCrew(CrewDeleteDTO crewDeleteDTO) {
        Crew crew = crewCommendRepository.findById(crewDeleteDTO.getId()).get();
        crew.setCrewIsDeleted('Y');
    }


    // ////////////////////////////클래스 내에서 사용되는 함수들////////////////////////////

    private Crew setDefaultCrewValues(Crew crew) {
        LocalDateTime now = LocalDateTime.now();
        crew.setCrewIsRecruit('Y');
        crew.setCrewCreateDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        if (crew.getCrewMaxPeople() == null) crew.setCrewMaxPeople(5);
        crew.setCrewIsDeleted('N');
        crew.setCrewBanDate(30);
        crew.setCrewRankGauge(1);

        return crew;
    }

    private static void modifyCrewEntity(CrewModifyDTO crewModifyDTO, Crew crew) {
        if (!crew.getCrewName().equals(crewModifyDTO.getCrewName()))
            crew.setCrewName(crewModifyDTO.getCrewName());

        if (!(crew.getCrewIsRecruit() == crewModifyDTO.getCrewIsRecruit()))
            crew.setCrewIsRecruit(crewModifyDTO.getCrewIsRecruit());

        if (!crew.getCrewMaxPeople().equals(crewModifyDTO.getCrewMaxPeople()))
            crew.setCrewMaxPeople(crewModifyDTO.getCrewMaxPeople());

        if (!crew.getCrewIntro().equals(crewModifyDTO.getCrewIntro()))
            crew.setCrewIntro(crewModifyDTO.getCrewIntro());

        if (!(crew.getCrewBanDate() == crewModifyDTO.getCrewBanDate()))
            crew.setCrewBanDate(crewModifyDTO.getCrewBanDate());
    }

    private CrewMember setCrewMemberInfo(Long crewId, Long cumId) {
        LocalDateTime now = LocalDateTime.now();
        CrewMember crewMember = new CrewMember();

        crewMember.setCrewMemberJoinDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewMember.setCrewMemberRoleUpdateDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewMember.setCrewId(crewId);
        crewMember.setCumId(cumId);
        crewMember.setCrewRoleId(2L);
        return crewMember;
    }

    private CrewMemberHistory setCrewMemberHistoryInfo(Long crewId, Long cumId, Long crewMemberRole) {
        CrewMemberHistory crewMemberHistory = new CrewMemberHistory();
        LocalDateTime now = LocalDateTime.now();

        crewMemberHistory.setCrewRoleId(crewMemberRole);
        crewMemberHistory.setCrewId(crewId);
        crewMemberHistory.setCrewMemberHistoryJoinDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewMemberHistory.setCrewMemberHistoryStateUpdateDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewMemberHistory.setCrewMemberHistoryState("JOINED");
        crewMemberHistory.setCrewMemberHistoryUpdateReason("크루 가입");
        crewMemberHistory.setCumId(cumId);
        return crewMemberHistory;
    }
}
