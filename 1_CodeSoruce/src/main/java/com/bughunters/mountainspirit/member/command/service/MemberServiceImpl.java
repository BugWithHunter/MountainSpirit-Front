package com.bughunters.mountainspirit.member.command.service;

import com.bughunters.mountainspirit.member.command.dto.RequestModifyStatusOfMemberDTO;
import com.bughunters.mountainspirit.member.command.dto.ResponseStatusDTO;
import com.bughunters.mountainspirit.member.command.entity.Member;
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
        Member member = memberRepository.findById(modifyStatusOfMemberDTO.getCumId()).orElse(null);

        if(member == null)
        {
            throw new IllegalStateException("Member not found");
        }
        Long previousRankId = member.getMemRankId() == null ? 0 : member.getMemRankId();
        // 합산 점수 적용
        int score = member.getScore() + modifyStatusOfMemberDTO.getSummaryScore();
        member.setScore(score);

        int findKey = modifyStatusOfMemberDTO.getBaseMemberRanks()
                .keySet()
                .stream()
                .sorted(Integer::compareTo) // 오름순 정렬
                .filter(x -> x >= score)
                .findFirst()
                .orElse(//해당 되는게 없으면 최대등급을 초과하는 점수로 최고 등급으로 update
                        modifyStatusOfMemberDTO
                                .getBaseMemberRanks()
                                .keySet()
                                .stream()
                                .max(Integer::compareTo).get());
        Long findRankId = modifyStatusOfMemberDTO.getBaseMemberRanks().get(findKey);

        member.setMemRankId(findRankId);

        ResponseStatusDTO responseStatusDTO = new ResponseStatusDTO();
        responseStatusDTO.setScore(score);
        // 등급업으로 프론트에 변경 됐다는것을 알리기 위함
        if(previousRankId.equals(member.getMemRankId())){
            responseStatusDTO.setModifyMemberRank(true);
        }

//        memberRepository.save(member);

        return responseStatusDTO;
    }
}
