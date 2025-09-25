package com.bughunters.mountainspirit.crewmember.command.service;

import com.bughunters.mountainspirit.crewmember.command.dto.CrewApplyDTO;
import com.bughunters.mountainspirit.crewmember.command.entity.CrewApply;
import com.bughunters.mountainspirit.crewmember.command.repository.CrewMemberApplyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CrewMemberServiceImpl implements CrewMemberService {
    private final ModelMapper modelMapper;
    CrewMemberApplyRepository crewMemberApplyRepository;

    public CrewMemberServiceImpl(ModelMapper modelMapper, CrewMemberApplyRepository crewMemberApplyRepository) {
        this.modelMapper = modelMapper;
        this.crewMemberApplyRepository = crewMemberApplyRepository;
    }

    @Override
    public void crewApplyRequest(CrewApplyDTO crewApplyDTO) {
        CrewApply crewApply = new CrewApply();
        LocalDateTime now = LocalDateTime.now();
        crewApply.setCrewApplyDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewApply.setCrewId(crewApplyDTO.getCrewId());
        crewApply.setCumId(crewApplyDTO.getCumId());

        crewMemberApplyRepository.save(crewApply);
    }
}
