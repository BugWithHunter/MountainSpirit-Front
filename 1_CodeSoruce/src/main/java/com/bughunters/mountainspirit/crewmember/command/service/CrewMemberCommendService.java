package com.bughunters.mountainspirit.crewmember.command.service;

import com.bughunters.mountainspirit.crewmember.command.dto.CrewApplyDTO;
import com.bughunters.mountainspirit.crewmember.command.dto.CrewIdentifyMemberDTO;
import com.bughunters.mountainspirit.crewmember.command.dto.CrewMemberAuthModifyDTO;

public interface CrewMemberCommendService {
    void crewApplyRequest(CrewApplyDTO crewApplyDTO);

    void crewApplyCancel(CrewApplyDTO crewApplyDTO);

    void registCrewMemberByCrewApplyApprove(CrewApplyDTO crewApplyDTO);

    void crewApplyRejected(CrewApplyDTO crewApplyDTO);

    void leaveCrew(CrewIdentifyMemberDTO crewIdentifyMemberDTO);

    void registCrewRole(String crewRole);

    void modifyCrewRole(CrewMemberAuthModifyDTO crewMemberAuthModifyDTO);
}
