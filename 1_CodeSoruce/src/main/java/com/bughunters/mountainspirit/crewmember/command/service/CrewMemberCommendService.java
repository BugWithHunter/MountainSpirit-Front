package com.bughunters.mountainspirit.crewmember.command.service;

import com.bughunters.mountainspirit.crewmember.command.dto.CrewApplyDTO;

public interface CrewMemberCommendService {
    void crewApplyRequest(CrewApplyDTO crewApplyDTO);

    void crewApplyCancel(CrewApplyDTO crewApplyDTO);
}
