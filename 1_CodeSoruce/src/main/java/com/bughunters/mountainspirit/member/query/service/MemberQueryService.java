package com.bughunters.mountainspirit.member.query.service;

import com.bughunters.mountainspirit.member.query.dto.BlackListDTO;

public interface MemberQueryService {
    BlackListDTO findBlakListByMemberId(Long id);
}
