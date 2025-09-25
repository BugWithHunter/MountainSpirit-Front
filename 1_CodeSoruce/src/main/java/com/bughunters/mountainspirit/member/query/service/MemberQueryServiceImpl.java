package com.bughunters.mountainspirit.member.query.service;

import com.bughunters.mountainspirit.member.query.dto.FindClimbCheckQueryDTO;
import com.bughunters.mountainspirit.member.query.dto.RequestStartClimbMountainQueryDTO;
import com.bughunters.mountainspirit.member.query.mapper.MemberQueryRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberQueryRepository memberQueryRepository;

    public MemberQueryServiceImpl(MemberQueryRepository memberQueryRepository) {
        this.memberQueryRepository = memberQueryRepository;
    }

    @Override
    public FindClimbCheckQueryDTO findClimbCheckByMember(RequestStartClimbMountainQueryDTO request) {

        FindClimbCheckQueryDTO findClimbCheckQueryDTO =
                memberQueryRepository.selectClimbCheckByMember(request);

        return findClimbCheckQueryDTO;
    }
}
