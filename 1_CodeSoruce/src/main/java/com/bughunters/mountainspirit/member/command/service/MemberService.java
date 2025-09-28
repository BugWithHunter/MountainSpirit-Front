package com.bughunters.mountainspirit.member.command.service;

import com.bughunters.mountainspirit.member.command.dto.RequestModifyStatusOfMemberDTO;
import com.bughunters.mountainspirit.member.command.dto.ResponseStatusDTO;
import com.bughunters.mountainspirit.member.command.entity.Member;

public interface MemberService {
    ResponseStatusDTO modifyStatusAfterClimbMountian(RequestModifyStatusOfMemberDTO modifyStatusOfMemberDTO);

    Member getTest(Long id);

    void setMemberCrewId(Long cumId, Long crewId);
}
