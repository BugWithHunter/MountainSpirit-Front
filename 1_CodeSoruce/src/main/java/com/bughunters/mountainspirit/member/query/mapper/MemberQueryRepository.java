package com.bughunters.mountainspirit.member.query.mapper;

import com.bughunters.mountainspirit.member.query.dto.BlackListDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberQueryRepository {
    BlackListDTO selectBlackListByMember(Long id);
}
