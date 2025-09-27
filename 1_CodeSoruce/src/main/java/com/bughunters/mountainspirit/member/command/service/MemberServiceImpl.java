package com.bughunters.mountainspirit.member.command.service;

import com.bughunters.mountainspirit.member.command.dto.RequestModifyStatusOfMemberDTO;
import com.bughunters.mountainspirit.member.command.dto.ResponseStatusDTO;
import com.bughunters.mountainspirit.member.command.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public ResponseStatusDTO modifyStatusAfterClimbMountian(RequestModifyStatusOfMemberDTO modifyStatusOfMemberDTO) {

    }
}
