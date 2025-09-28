package com.bughunters.mountainspirit.memberrank.query.service;

import com.bughunters.mountainspirit.memberrank.query.dto.HigherClimber;
import com.bughunters.mountainspirit.memberrank.query.mapper.MemberRankQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberRankQueryServiceImpl implements MemberRankQueryService {

    private final MemberRankQueryRepository memberRankQueryRepository;

    public MemberRankQueryServiceImpl(MemberRankQueryRepository memberRankQueryRepository) {
        this.memberRankQueryRepository = memberRankQueryRepository;
    }

    @Override
    public List<HigherClimber> selectLotsOfClimbingByMountain(String frtrlId) {

        List<HigherClimber> climberDTOs = memberRankQueryRepository.selectHigherClimberByMountain(frtrlId);


        return climberDTOs;
    }
}
