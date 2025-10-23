package com.bughunters.mountainspirit.admin.query.service;

import com.bughunters.mountainspirit.admin.query.dto.*;

import java.util.List;

public interface AdminMemberService {

    // 회원 목록 조회 (페이지네이션)
    List<MemberInfoDTO> getAllMembers(Integer page, Integer size);

    // 회원 통계 조회 (전체 기준)
    MemberInfoStatsDTO getMemberStats();

    List<MemberLoginInfoDTO> getMemberLoginInfo(Integer page, Integer size);

    MemberLoginInfoStatsDTO getMemberLoginStats();

    List<MemberRankInfoDTO> getMemberRankInfo(Integer page, Integer size);

    List<MemberRankStatsDTO> getMemberRankStats();
}
