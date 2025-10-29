package com.bughunters.mountainspirit.member.query.mapper;

import com.bughunters.mountainspirit.member.query.dto.RequestLoginwithAuthoritiesDTO;
import com.bughunters.mountainspirit.member.query.dto.BlackListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberQueryRepository {
    BlackListDTO selectBlackListByMember(Long id);


    RequestLoginwithAuthoritiesDTO selectByIdWithAuthorite(String email);
}
