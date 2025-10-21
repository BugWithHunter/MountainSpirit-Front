package com.bughunters.mountainspirit.admin.query.service;

import com.bughunters.mountainspirit.admin.query.dto.MemberInfoDTO;
import com.bughunters.mountainspirit.admin.query.dto.MemberInfoStatsDTO;

public interface AdminMemberService {

    // 회원 목록 조회 (페이지네이션)
    MemberInfoDTO getAllMembers(Integer page, Integer size);

    // 회원 통계 조회 (전체 기준)
    MemberInfoStatsDTO getMemberStats();

}
