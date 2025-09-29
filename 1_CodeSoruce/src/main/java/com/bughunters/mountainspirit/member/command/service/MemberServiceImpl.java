package com.bughunters.mountainspirit.member.command.service;

import com.bughunters.mountainspirit.member.command.dto.*;
import com.bughunters.mountainspirit.member.command.entity.Member;
import com.bughunters.mountainspirit.member.command.repository.MemberRepository;
import com.bughunters.mountainspirit.member.query.dto.BlackListDTO;
import com.bughunters.mountainspirit.member.query.service.MemberQueryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryService memberQueryService;
    private final ModelMapper modelMapper;

    public MemberServiceImpl(MemberRepository memberRepository
            , MemberQueryService memberQueryService
            , ModelMapper modelMapper) {
        this.memberRepository = memberRepository;
        this.memberQueryService = memberQueryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseStatusDTO modifyStatusAfterClimbMountian(RequestModifyStatusOfMemberDTO modifyStatusOfMemberDTO) {
        Member member = memberRepository.findById(modifyStatusOfMemberDTO.getCumId()).orElse(null);

        if (member == null) {
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
        responseStatusDTO.setMemRankId(findRankId);
        responseStatusDTO.setCumNm(member.getMemName());

        // 등급업으로 프론트에 변경 됐다는것을 알리기 위함
        if (!previousRankId.equals(member.getMemRankId())) {
            responseStatusDTO.setModifyMemberRank(true);
        }

//        memberRepository.save(member);

        return responseStatusDTO;
    }

    @Override
    public Member getTest(Long id) {
        return memberRepository.findById(id).orElse(null);

    }

    @Override
    public void setMemberCrewId(Long cumId, Long crewId) {
        Member member = memberRepository.findById(cumId).orElse(null);

        if (member == null) {
            throw new NullPointerException("Member not found");
        }

        member.setCrewId(crewId);
    }

    //회원 가입
    @Override
    public ResponseSignUpDTO signUp(RequestMemberDTO member) {


        return checkBeforeSignUp(member);
    }

    @Override
    public ResponseQuitDTO memberQuit(RequestQuitMemberDTO memberDTO) {
        ResponseQuitDTO responseQuitDTO = new ResponseQuitDTO();
        Member member = memberRepository.findById(memberDTO.getId()).orElse(null);

        if (member == null) { //테스트중에는 값을 잘못 입력해서 null 이 나올수 있어서 처리 함.
            return null;
        }

        //암호가 틀렸을 때
        if (!member.getMemPwd().equals(memberDTO.getMemPwd())) {
            responseQuitDTO.setInvalidPwd(true);
            return responseQuitDTO;
        }

        member.setQuitDate(LocalDateTime.now());
        member.setMemStsId(2L);
        memberRepository.save(member);

        return responseQuitDTO;
    }

    // 회원 가입 제한 사항 확인, 이메일 중복, 이미 가입한 회원, 블랙리스트 등등
    private ResponseSignUpDTO checkBeforeSignUp(RequestMemberDTO memberDTO) throws IllegalArgumentException {

        ResponseSignUpDTO responseSignUpDTO = new ResponseSignUpDTO();
        // 중복된 email 혹은 이미 가입된 회원이 있는지조회(이미 가입된 회원 조건은 이름 && 생일이 같은 사람이있는지로 판단)
        Member findMember
                = memberRepository.findByEmailOrMemNameAndBirth(memberDTO.getEmail(), memberDTO.getMemName(), memberDTO.getBirth());

        if (findMember != null) {
            //중복 이메일이 있는지 확인
            if (findMember.getEmail().equals(memberDTO.getEmail())) {
                responseSignUpDTO.setDuplicateEmail(true);
                return responseSignUpDTO;
            }

            if (findMember != null) {
                BlackListDTO blackListDTO = memberQueryService.findBlakListByMemberId(findMember.getId());

                // null이 아니면 블랙리스트라 가입 거절
                if (blackListDTO != null) {
                    responseSignUpDTO.setBanMember(true);
                    responseSignUpDTO.setBlackListDTO(blackListDTO);
                    return responseSignUpDTO;
                }

                responseSignUpDTO.setExistingMember(true);
                return responseSignUpDTO;
            }

        } else {
            try {
                Member member = modelMapper.map(memberDTO, Member.class);
                member.setMemStsId(1L); //회원 상태 정상 상태로 초기 셋팅
                member.setSignInDate(LocalDate.now());
                member = memberRepository.save(member);
                responseSignUpDTO.setMemberDTO(modelMapper.map(member, ResponseMemberDTO.class));
            } catch (Exception e) {
                throw new IllegalArgumentException("Member not Exception");
            }
        }

        return responseSignUpDTO;
    }
}
