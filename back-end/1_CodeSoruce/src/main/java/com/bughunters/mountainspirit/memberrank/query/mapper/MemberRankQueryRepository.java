package com.bughunters.mountainspirit.memberrank.query.mapper;

import com.bughunters.mountainspirit.memberrank.query.dto.HigherClimber;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberRankQueryRepository {

    List<HigherClimber> selectHigherClimberByMountain(String frtrlId);
}
