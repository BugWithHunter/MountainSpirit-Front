package com.bughunters.mountainspirit.crewmember.command.service;

import com.bughunters.mountainspirit.crewmember.command.dto.CrewApplyDTO;
import com.bughunters.mountainspirit.crewmember.command.entity.CrewApply;
import com.bughunters.mountainspirit.crewmember.command.entity.CrewApplyHistory;
import com.bughunters.mountainspirit.crewmember.command.repository.CrewMemberCommendRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class CrewMemberCommendServiceImpl implements CrewMemberCommendService {
    private final ModelMapper modelMapper;
    CrewMemberCommendRepository crewMemberCommendRepository;

    public CrewMemberCommendServiceImpl(ModelMapper modelMapper, CrewMemberCommendRepository crewMemberCommendRepository) {
        this.modelMapper = modelMapper;
        this.crewMemberCommendRepository = crewMemberCommendRepository;
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

        crewMemberCommendRepository.save(crewApply);
    }

    @Override
    @Transactional
    public void crewApplyCancel(CrewApplyDTO crewApplyDTO) {
        // 크루 신청 정보 검색
        CrewApply crewApply = crewMemberCommendRepository.findByCrewIdAndCumId(crewApplyDTO.getCrewId(),crewApplyDTO.getCumId());

        // 크루 가입 신청 삭제
        crewMemberCommendRepository.delete(crewApply);

    }

}
