package com.bughunters.mountainspirit.member.query.service;

import com.bughunters.mountainspirit.member.query.dto.RequestLoginwithAuthoritiesDTO;
import com.bughunters.mountainspirit.member.query.dto.BlackListDTO;

import java.util.List;

public interface MemberQueryService {
    BlackListDTO findBlakListByMemberId(Long id);

    RequestLoginwithAuthoritiesDTO findMemberWithAuthoriesByEmail(String email);
}
