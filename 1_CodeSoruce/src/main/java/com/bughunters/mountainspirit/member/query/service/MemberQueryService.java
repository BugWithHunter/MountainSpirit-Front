package com.bughunters.mountainspirit.member.query.service;


import com.bughunters.mountainspirit.member.query.dto.FindClimbCheckQueryDTO;
import com.bughunters.mountainspirit.member.query.dto.RequestStartClimbMountainQueryDTO;

public interface MemberQueryService {
    FindClimbCheckQueryDTO findClimbCheckByMember(RequestStartClimbMountainQueryDTO request);
}
