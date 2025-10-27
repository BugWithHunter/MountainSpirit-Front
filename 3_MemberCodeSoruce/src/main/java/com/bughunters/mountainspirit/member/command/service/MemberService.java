package com.bughunters.mountainspirit.member.command.service;

import com.bughunters.mountainspirit.member.command.dto.*;
import com.bughunters.mountainspirit.member.command.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    ResponseStatusDTO modifyStatusAfterClimbMountian(RequestModifyStatusOfMemberDTO modifyStatusOfMemberDTO);

    ResponseMemberDTO findMember(Long id);

    void setMemberCrewId(Long cumId, Long crewId);

    ResponseSignUpDTO signUp(RequestMemberDTO member);

    ResponseQuitDTO memberQuit(RequestQuitMemberDTO member);

    UserDetails loadUserByUsername(String email);

    void updateInvlidPassword(RequsetloginHisotry user);

    void updateCompleteLogin(RequsetloginHisotry loginHistory);

    void registCrewId(long crewId, long cumId);

    boolean updateStatus(Long id, ReportMemberUpdateDTO dto);


    ResponseProfileImageDTO updateProfileImage(MultipartFile singleFile, Long id, HttpServletRequest request);
    void deleteCrewId(long crewId, long cumId);
}
