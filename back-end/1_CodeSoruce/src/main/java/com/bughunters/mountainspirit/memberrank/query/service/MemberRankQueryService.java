package com.bughunters.mountainspirit.memberrank.query.service;

import com.bughunters.mountainspirit.memberrank.query.dto.HigherClimber;

import java.util.List;

public interface MemberRankQueryService {
    List<HigherClimber> selectLotsOfClimbingByMountain(String frtrlId);
}
