package com.bughunters.mountainspirit.crewmember.command.service;

import com.bughunters.mountainspirit.crewmember.command.dto.CrewApplyDTO;
import com.bughunters.mountainspirit.crewmember.command.dto.CrewIdentifyMemberDTO;

public interface CrewMemberCommendService {
    void crewApplyRequest(CrewApplyDTO crewApplyDTO);

    void crewApplyCancel(CrewApplyDTO crewApplyDTO);

    void registCrewMemberByCrewApplyApprove(CrewApplyDTO crewApplyDTO);

    void crewApplyRejected(CrewApplyDTO crewApplyDTO);

    void leaveCrew(CrewIdentifyMemberDTO crewIdentifyMemberDTO);

    void registCrewRole(String crewRole);
}
