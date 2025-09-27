package com.bughunters.mountainspirit.crewmember.query.service;

import com.bughunters.mountainspirit.crewmember.query.dto.CrewApplyListDTO;
import com.bughunters.mountainspirit.crewmember.query.mapper.CrewMemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class CrewMemberQueryServiceImpl implements CrewMemberQueryService {
    private CrewMemberMapper crewMemberMapper;

    @Autowired
    public CrewMemberQueryServiceImpl (CrewMemberMapper crewMemberMapper) {
        this.crewMemberMapper = crewMemberMapper;
    }

    @Override
    @Transactional
    public CrewApplyListDTO findAllCrewApplied(CrewApplyListDTO crewApplyListDTO) {
        long crewId = crewApplyListDTO.getCrewId();
        crewApplyListDTO = crewMemberMapper.findAllCrewAppliedByCrewId(crewId);
        return crewApplyListDTO;
    }

    @Override
    @Transactional
    public boolean checkCrewApplyIsBanned(Long crewId, Long cumId) {
        log.info("넘어온 크루 아이디 + 유저 아이디 : {} {}",crewId,cumId);
        String date = crewMemberMapper.checkCrewBannedDateByCrewIdAndCumId(crewId,cumId);
        if(date==null){
            log.info("밴 당한 기록이 없습니다.");
            return false;
        }
        else{
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime forCompareDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).plusMonths(1);
            return now.isBefore(forCompareDate);
        }
    }
}
