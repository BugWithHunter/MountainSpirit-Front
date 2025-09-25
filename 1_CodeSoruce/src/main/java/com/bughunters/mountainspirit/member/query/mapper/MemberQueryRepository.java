package com.bughunters.mountainspirit.member.query.mapper;

import com.bughunters.mountainspirit.member.query.dto.FindClimbCheckQueryDTO;
import com.bughunters.mountainspirit.member.query.dto.RequestStartClimbMountainQueryDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberQueryRepository {
    FindClimbCheckQueryDTO selectClimbCheckByMember(RequestStartClimbMountainQueryDTO request);
}
