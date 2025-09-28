package com.bughunters.mountainspirit.member.command.service;

import com.bughunters.mountainspirit.member.command.dto.RequestModifyStatusOfMemberDTO;
import com.bughunters.mountainspirit.member.command.dto.ResponseStatusDTO;

public interface MemberService {
    ResponseStatusDTO modifyStatusAfterClimbMountian(RequestModifyStatusOfMemberDTO modifyStatusOfMemberDTO);
}
