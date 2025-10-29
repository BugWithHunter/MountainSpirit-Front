package com.bughunters.mountainspirit.member.command.service;


import com.bughunters.mountainspirit.member.command.dto.*;
import com.bughunters.mountainspirit.member.command.entity.*;
import com.bughunters.mountainspirit.member.command.repository.*;
import com.bughunters.mountainspirit.member.query.dto.BlackListDTO;
import com.bughunters.mountainspirit.member.command.dto.RequestLoginwithAuthoritiesDTO;
import com.bughunters.mountainspirit.member.query.service.MemberQueryService;
import com.bughunters.mountainspirit.security.MemberStatusAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryService memberQueryService;
    private final ModelMapper modelMapper;
    private final LoginFailureRecordRepository loginFailureRecordRepository;
    private final LoginRecordRepository loginRecordRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthorityRepository authorityRepository;
    private final MemberAuthorityRepository memberAuthorityRepository;
    private final ProfileImageService profileImageService;
    private final ProfileImageRepository profileImageRepository;
    private final MemberRankRepository memberRankRepository;

    private final PasswordEncoder passwordEncoder;  //평문과 암호화 된 다이제스트를 비교하기 위한 도구

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository
            , MemberQueryService memberQueryService
            , ModelMapper modelMapper
            , BCryptPasswordEncoder bCryptPasswordEncoder
            , LoginFailureRecordRepository loginFailureRecordRepository
            , LoginRecordRepository loginRecordRepository
            , AuthorityRepository authorityRepository
            , MemberAuthorityRepository memberAuthorityRepository
            , ProfileImageService profileImageService
            , ProfileImageRepository profileImageRepository
            , MemberRankRepository memberRankRepository
            , PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.memberQueryService = memberQueryService;
        this.modelMapper = modelMapper;
        this.loginFailureRecordRepository = loginFailureRecordRepository;
        this.loginRecordRepository = loginRecordRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authorityRepository = authorityRepository;
        this.memberAuthorityRepository = memberAuthorityRepository;
        this.profileImageService = profileImageService;
        this.profileImageRepository = profileImageRepository;
        this.memberRankRepository = memberRankRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //등산 이후 회원 정보 변경
    @Override
    @Transactional
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
                .sorted(Comparator.reverseOrder()) // 내림차순 정렬
                .filter(x -> x <= score)
                .findFirst()
                .orElse(//해당 되는게 없으면 최대등급을 초과하는 점수로 최고 등급으로 update
                        modifyStatusOfMemberDTO
                                .getBaseMemberRanks()
                                .keySet()
                                .stream()
                                .max(Integer::compareTo).get());
        Long findRankId = modifyStatusOfMemberDTO.getBaseMemberRanks().get(findKey);

        member.setMemRankId(findRankId);
        memberRepository.saveAndFlush(member);

        ResponseStatusDTO responseStatusDTO = new ResponseStatusDTO();
        responseStatusDTO.setScore(score);
        responseStatusDTO.setMemRankId(findRankId);
        responseStatusDTO.setCumNm(member.getMemName());

        // 등급업으로 프론트에 변경 됐다는것을 알리기 위함
        if (!previousRankId.equals(member.getMemRankId())) {
            responseStatusDTO.setModifyMemberRank(true);
        }

        return responseStatusDTO;
    }

    @Override
    public ResponseMemberDTO findMember(Long id) {

        Member member = memberRepository.findById(id).orElse(null);
        ResponseMemberDTO responseMemberDTO = member != null ?
                modelMapper.map(member, ResponseMemberDTO.class) : null;

        if(responseMemberDTO != null){
            ProfileOfMember profile = profileImageRepository.findByCumId(responseMemberDTO.getId());
            String profilePath = profile != null ? profile.getFilePath() : null;
            responseMemberDTO.setProfilePath(profilePath);

            MemberRank memberRank = memberRankRepository.findById(responseMemberDTO.getMemRankId()).orElse(null);
            String rankName = memberRank != null ? memberRank.getName() : null;
            responseMemberDTO.setRankName(rankName);

        }
        return responseMemberDTO;
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
    @Transactional
    public ResponseSignUpDTO signUp(RequestMemberDTO member) {
        return checkBeforeSignUp(member);
    }

    //회원 탈퇴
    @Override
    @Transactional
    public ResponseQuitDTO memberQuit(RequestQuitMemberDTO memberDTO) {
        ResponseQuitDTO responseQuitDTO = new ResponseQuitDTO();
        Member member = memberRepository.findById(memberDTO.getId()).orElse(null);

        if (member == null) { //테스트중에는 값을 잘못 입력해서 null 이 나올수 있어서 처리 함.
            return null;
        }

        //암호가 틀렸을 때
        if (bCryptPasswordEncoder.matches(member.getMemPwd(), memberDTO.getMemPwd())) {
            responseQuitDTO.setInvalidPwd(true);
            return responseQuitDTO;
        }

        member.setQuitDate(LocalDateTime.now());
        member.setMemStsId(2L);
        memberRepository.save(member);

        return responseQuitDTO;
    }

    //spring security에서 회원정보 조회에 사용
    @Override
    public UserDetails loadUserByUsername(String email) {
        com.bughunters.mountainspirit.member.query.dto.RequestLoginwithAuthoritiesDTO loginDTO =
                memberQueryService.findMemberWithAuthoriesByEmail(email);

        if (loginDTO == null)
            return null;

        //계정 권한
        List<GrantedAuthority> grantedAuthorities
                = loginDTO.getAuthorities().stream()
                .map(x -> new SimpleGrantedAuthority(x.getAuthDescribe()))
                .collect(Collectors.toList());

        UserImpl userImpl =
                new UserImpl(
                        loginDTO.getEmail(),
                        loginDTO.getPwd(),
                        grantedAuthorities
                );
        userImpl.setDetails(modelMapper.map(loginDTO, RequestLoginwithAuthoritiesDTO.class));

        return userImpl;
    }

    //로그인 실패시 실패이력 insert 및 회원테이블 실패 횟 수 업데이트
    @Override
    @Transactional
    public void updateInvlidPassword(RequsetloginHisotry requestLoginHisotry) {
        Member member = memberRepository.findById(requestLoginHisotry.getCumId()).orElse(null);
        if (member == null)
            return;

        LoginFailureRecord loginFailureRecord = new LoginFailureRecord(
                null
                , requestLoginHisotry.getDateTime()
                , requestLoginHisotry.getClientIp()
                , requestLoginHisotry.getReason()
                , member.getId()
        );

        int failCount = member.getLoginFailCnt() == null
                ? 1 : member.getLoginFailCnt() + 1;

        //연속 오 입력이 6이 됐다는건 15분 지나고 또 틀렸기 때문 .
        // 1로 바꾸지 않으면 바로 접속 제한 걸려서 1부터 다시 카운트 시작
        failCount = failCount >= 6 ? 1 : failCount;

        member.setLoginFailCnt(failCount);


        if (failCount >= 5) {
            LocalDateTime loginLockUnitlby = LocalDateTime.now().plusMinutes(15);
            member.setLoginLockUntil(loginLockUnitlby); // 15분간 접속 불가
        }
        loginFailureRecordRepository.save(loginFailureRecord);
        memberRepository.save(member);
    }

    //정상 로그인 hitory 기록
    @Override
    @Transactional
    public void updateCompleteLogin(RequsetloginHisotry loginHistory) {
        LoginRecord loginRecord = new LoginRecord(
                null,
                loginHistory.getDateTime(),
                loginHistory.getClientIp(),
                "",
                loginHistory.getCumId()
        );

        Member member = memberRepository.findById(loginHistory.getCumId()).orElse(null);
        member.setLoginFailCnt(0);
        member.setLastLogin(loginRecord.getDate());
        memberRepository.save(member);
        loginRecordRepository.save(loginRecord);
    }

    // Member id 를 받아 crew id 삽입
    @Override
    @Transactional
    public void registCrewId(long crewId, long cumId) {
        Member member = memberRepository.findById(cumId).orElse(null);
        log.info("service member 정보 : {}", member);
        if (member == null)
            return;
        member.setCrewId(crewId);
        log.info("feign 통신 받는쪽 끝났어요");
        memberRepository.flush();
        log.info("flust 실행");
    }

    @Override
    public boolean updateStatus(Long id, ReportMemberUpdateDTO dto) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isEmpty()) return false;

        Member member = optionalMember.get();

        if (dto.getMemStsId() != null) {
            member.setMemStsId(dto.getMemStsId());
        }
        if (dto.getBanCnt() != null) {
            member.setBanCnt(dto.getBanCnt());
        }

        memberRepository.save(member);
        return true;
    }

    @Override
    @Transactional
    public ResponseProfileImageDTO updateProfileImage(MultipartFile singleFile, Long id, HttpServletRequest request) {
        ResponseProfileImageDTO responseProfileImageDTO = null;
        try {
            responseProfileImageDTO = profileImageService.updateProfileImage(singleFile, id , request);

            ProfileOfMember findProfile = profileImageRepository.findByCumId(id);

            //파일이 없었을때
            if(findProfile == null){
                findProfile = modelMapper.map(responseProfileImageDTO, ProfileOfMember.class);
            } else { // 기존 파일이있어서 덮어 쓰기
                findProfile.setFilePath(responseProfileImageDTO.getFilePath());
                findProfile.setDirPath(responseProfileImageDTO.getDirPath());
            }
            profileImageRepository.save(findProfile);



        } catch (IOException e) {
            if (responseProfileImageDTO == null) {
                responseProfileImageDTO = new ResponseProfileImageDTO();
                responseProfileImageDTO.setSuccessUpload(false);
                responseProfileImageDTO.setExceptionMessage("프로필 변경 실패");
                log.info(e.getMessage());
            }
        }

        return responseProfileImageDTO;
    }
    public void deleteCrewId(long crewId, long cumId) {
        Member member = memberRepository.findById(cumId).get();
        log.info("삭제할 회원 정보 : {}",member);
        member.setCrewId(null);
    }

    @Override
    public boolean checkPassowrd(CheckPasswordDTO checkPasswordDTO) {
        /* 설명. DB에 있는 해당 회원의 정보 */
        Member member =  memberRepository.findById(checkPasswordDTO.getId()).orElse(null);
        if(member == null || !passwordEncoder.matches(checkPasswordDTO.getPassword(), member.getMemPwd())){
            return false;
        }

        return true;
    }

    @Override
    public boolean modifyPassword(ModifyPasswordDTO modifyPasswordDTO) {
        Member member =  memberRepository.findById(modifyPasswordDTO.getId()).orElse(null);

        if(member == null || !passwordEncoder.matches(modifyPasswordDTO.getOldPassword(), member.getMemPwd())){
            return false;
        }
        // BCrypt 암호화
        modifyPasswordDTO.setNewPassword(bCryptPasswordEncoder.encode(modifyPasswordDTO.getNewPassword()));

        member.setMemPwd(modifyPasswordDTO.getNewPassword());
        memberRepository.save(member);

        return true;
    }


    // 회원 가입 제한 사항 확인, 이메일 중복, 이미 가입한 회원, 블랙리스트 등등
    private ResponseSignUpDTO checkBeforeSignUp(RequestMemberDTO memberDTO) throws IllegalArgumentException {

        ResponseSignUpDTO responseSignUpDTO = new ResponseSignUpDTO();
        // 중복된 email 혹은 이미 가입된 회원이 있는지조회(이미 가입된 회원 조건은 이름 && 생일이 같은 사람이있는지로 판단)
        Member findMember
                = memberRepository.findByEmailOrMemNameAndBirth(memberDTO.getEmail(), memberDTO.getMemName(), memberDTO.getBirth());

        List<AuthorityList> authorityList
                = authorityRepository.findAll().stream()
                .sorted(Comparator.comparing(AuthorityList::getId))
                .toList();

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
                member.setMemRankId(1L); //회원 상태 정상 상태로 초기 셋팅
                member.setSignInDate(LocalDate.now());
                member = memberRepository.save(member);
                responseSignUpDTO.setMemberDTO(modelMapper.map(member, ResponseMemberDTO.class));

                //기본 Member 권한 부여
                memberAuthorityRepository.save(new MemberAuthority(null, member.getId(), authorityList.get(0).getId()));
            } catch (Exception e) {
                throw new IllegalArgumentException("Member not Exception");
            }
        }

        return responseSignUpDTO;
    }


}