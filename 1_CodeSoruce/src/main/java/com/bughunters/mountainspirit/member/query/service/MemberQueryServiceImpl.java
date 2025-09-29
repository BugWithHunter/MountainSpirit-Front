package com.bughunters.mountainspirit.member.query.service;

import com.bughunters.mountainspirit.member.query.dto.BlackListDTO;
import com.bughunters.mountainspirit.member.query.mapper.MemberQueryRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberQueryServiceImpl implements MemberQueryService{
    private final MemberQueryRepository memberQueryRepository;

    public MemberQueryServiceImpl(MemberQueryRepository memberQueryRepository) {
        this.memberQueryRepository = memberQueryRepository;
    }

    @Override
    public BlackListDTO findBlakListByMemberId(Long id) {
        return memberQueryRepository.selectBlackListByMember(id);
    }
}
