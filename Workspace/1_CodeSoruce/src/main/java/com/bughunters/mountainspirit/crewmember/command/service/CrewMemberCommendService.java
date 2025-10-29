package com.bughunters.mountainspirit.crewmember.command.service;

import com.bughunters.mountainspirit.crewmember.command.dto.*;

public interface CrewMemberCommendService {
    boolean crewApplyRequest(CrewApplyDTO crewApplyDTO);

    void crewApplyCancel(CrewApplyDTO crewApplyDTO);

    void registCrewMemberByCrewApplyApprove(CrewApplyDTO crewApplyDTO);

    void crewApplyRejected(CrewApplyDTO crewApplyDTO);

    void leaveCrew(CrewIdentifyMemberDTO crewIdentifyMemberDTO);

    void registCrewRole(String crewRole);

    void modifyCrewRole(CrewMemberAuthModifyDTO crewMemberAuthModifyDTO);

    void modifyCrewMemberRole(CrewMemberRoleModifyDTO crewMemberRoleModifyDTO);

    void banCrewMember(CrewMemberBanDTO crewMemberBanDTO);
}
