package com.bughunters.mountainspirit.member.command.service;

import com.bughunters.mountainspirit.member.command.dto.*;
import com.bughunters.mountainspirit.member.command.entity.Member;
import org.springframework.security.core.userdetails.UserDetails;

public interface MemberService {
    ResponseStatusDTO modifyStatusAfterClimbMountian(RequestModifyStatusOfMemberDTO modifyStatusOfMemberDTO);

    Member getTest(Long id);

    void setMemberCrewId(Long cumId, Long crewId);

    ResponseSignUpDTO signUp(RequestMemberDTO member);

    ResponseQuitDTO memberQuit(RequestQuitMemberDTO member);

    UserDetails loadUserByUsername(String email);

    void updateInvlidPassword(RequsetloginHisotry user);

    void updateCompleteLogin(RequsetloginHisotry loginHistory);

    boolean updateStatus(Long id, ReportMemberUpdateDTO dto);
}
