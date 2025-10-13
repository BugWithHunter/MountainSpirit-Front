package com.bughunters.mountainspirit.crewmember.command.service;

import com.bughunters.mountainspirit.crewmember.command.dto.*;
import com.bughunters.mountainspirit.crewmember.command.entity.*;
import com.bughunters.mountainspirit.crewmember.command.infrastructure.MemberServiceClient;
import com.bughunters.mountainspirit.crewmember.command.repository.*;
//import com.bughunters.mountainspirit.member.command.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class CrewMemberCommendServiceImpl implements CrewMemberCommendService {
    private final ModelMapper modelMapper;
    CrewMemberCommendRepository crewMemberCommendRepository;
    CrewMemberHistoryCommendRepository crewMemberHistoryCommendRepository;
    CrewApplyCommendRepository crewApplyCommendRepository;
    CrewApplyHistoryCommendRepository crewApplyHistoryCommendRepository;
    CrewMemberRoleCommendRepository crewMemberRoleCommendRepository;
    CrewMemberAuthCommendRepository crewMemberAuthCommendRepository;
    MemberServiceClient memberServiceClient;
//    MemberService memberService;

    @Autowired
    public CrewMemberCommendServiceImpl(ModelMapper modelMapper,
                                        CrewMemberCommendRepository crewMemberCommendRepository,
                                        CrewMemberHistoryCommendRepository crewMemberHistoryCommendRepository,
                                        CrewApplyCommendRepository crewApplyCommendRepository,
                                        CrewApplyHistoryCommendRepository crewApplyHistoryCommendRepository,
                                        CrewMemberRoleCommendRepository crewMemberRoleCommendRepository,
                                        CrewMemberAuthCommendRepository crewMemberAuthCommendRepository,
//                                        MemberService memberService) {
                                        MemberServiceClient memberServiceClient) {
        this.modelMapper = modelMapper;
        this.crewMemberCommendRepository = crewMemberCommendRepository;
        this.crewMemberHistoryCommendRepository = crewMemberHistoryCommendRepository;
        this.crewApplyCommendRepository = crewApplyCommendRepository;
        this.crewApplyHistoryCommendRepository = crewApplyHistoryCommendRepository;
        this.crewMemberRoleCommendRepository = crewMemberRoleCommendRepository;
        this.crewMemberAuthCommendRepository = crewMemberAuthCommendRepository;
//        this.memberService = memberService;
        this.memberServiceClient = memberServiceClient;
    }

    @Override
    @Transactional
    public void crewApplyRequest(CrewApplyDTO crewApplyDTO) {
        // 이미 가입 신청이 되어있을 시 가입 신청 불가
        if (crewApplyCommendRepository.existsByCrewIdAndCumId(crewApplyDTO.getCrewId(), crewApplyDTO.getCumId())) {
            log.info("이미 크루 가입 신청이 되어 있습니다.");
            return;
        }


        CrewApply crewApply = new CrewApply();
        LocalDateTime now = LocalDateTime.now();
        crewApply.setCrewApplyDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewApply.setCrewId(crewApplyDTO.getCrewId());
        crewApply.setCumId(crewApplyDTO.getCumId());

        crewApplyCommendRepository.save(crewApply);
    }

    @Override
    @Transactional
    public void crewApplyCancel(CrewApplyDTO crewApplyDTO) {
        // 크루 가입 신청 정보 검색
        CrewApply crewApply = crewApplyCommendRepository.findByCrewIdAndCumId(crewApplyDTO.getCrewId(), crewApplyDTO.getCumId());

        // 크루 가입 신청 삭제
        crewApplyCommendRepository.delete(crewApply);
    }

    @Override
    public void registCrewMemberByCrewApplyApprove(CrewApplyDTO crewApplyDTO) {
        CrewApply crewApply = null;

        try {
            crewApply = crewMemberRegistTransaction(crewApplyDTO);
            log.info("크루 맴버 등록 transaction 작업 끝");

            // feign client 통신
            if (memberServiceClient.updateMemberCrewInfo(crewApplyDTO.getCrewId(), crewApplyDTO.getCumId()) == null)
                throw new Exception("feign client 통신 실패");
            log.info("feign client 통신 끝");
        } catch (Exception e) {
            // 만약 crewMemberRegistTransaction에서 트랜잭션 롤백이 일어나면 crewApply가 null로 넘어오게 되어 아래 롤백 트랜잭션 메소드가 실행되지 않음
            if (crewApply == null) {
                log.info("service 크루 신청 롤백 내용 없음");
                return;
            }

            crewApplyRollBackTransaction(crewApply);
            log.info("크루 맴버 롤백 transaction 끝");
        }
    }

    @Transactional
    public CrewApply crewMemberRegistTransaction(CrewApplyDTO crewApplyDTO) {
        CrewApply crewApply = null;

        try {
            crewApply = crewApplyCommendRepository.findByCrewIdAndCumId(crewApplyDTO.getCrewId(), crewApplyDTO.getCumId());
            log.info("service crewApply 값 : {}", crewApply.toString());

            crewApplyDTO.setId(crewApply.getId());
            crewApplyDTO.setCrewApplyDate(crewApply.getCrewApplyDate());

            // 크루구성원(CrewMember) 테이블에 크루구성원 데이터 insert
            CrewMember crewMember = setCrewMemberInfo(crewApplyDTO);
            crewMemberCommendRepository.save(crewMember);
            log.info("service crewMember 값 : {}", crewMember.toString());

            // 크루 가입 신청 히스토리(CrewApplyHistory)에 데이터 insert
            CrewApplyHistory crewApplyHistory = setCrewApplyHistoryInfo(crewApplyDTO, 'Y');
            log.info("service CrewApplyHistory 값 : {}", crewApplyHistory.toString());
            crewApplyHistoryCommendRepository.save(crewApplyHistory);


            // 크루 구성원 히스토리(CrewMemberHistory) 테이블에서 crewMemberHistoryState를 JOINED로 설정하고 insert
            CrewMemberHistory crewMemberHistory = setCrewMemberHistoryInfo(crewApplyDTO, 1L);
            log.info("service CrewMemberHistory 값 : {}", crewMemberHistory.toString());
            crewMemberHistoryCommendRepository.save(crewMemberHistory);

            // 회원(Member) 테이블에 크루 정보 수정
//        Member member = memberCommendRepository.findById(crewApplyDTO.getCumId()).get();
//        member.setCrewId(crewApplyDTO.getCrewId());
//        memberService.setMemberCrewId(crewApplyDTO.getCumId(),crewApplyDTO.getCrewId());

            // 크루 가입 신청(CrewApply) 테이블에서 신청 데이터 delete
            crewApplyCommendRepository.delete(crewApply);
            log.info("service 가입신청 삭제 완료");
        } catch (Exception e) {
            log.info("service 가입 신청 예외 발생");
            crewApply = null;
        }

        return crewApply;
        // 후에 DB에 쿼리문 여러번 날아가지 않게 ToString 수정을 하든지 해야 될듯하다, 테스트 케이스로 개선사항 작성하면 더 좋을듯하다.
    }

    @Transactional
    public void crewApplyRollBackTransaction(CrewApply crewApply) {
        log.info("service 크루 가입 승인 롤백 발생, 롤백 할 크루 신청 Entity : {}", crewApply.toString());
        CrewApplyDTO crewApplyDTO = modelMapper.map(crewApply, CrewApplyDTO.class);

        // 크루구성원(CrewMember) 테이블에서 데이터 delete
        CrewMember crewMember = crewMemberCommendRepository.findByCrewIdAndCumId(crewApplyDTO.getCrewId(), crewApplyDTO.getCumId());
        crewMemberCommendRepository.delete(crewMember);
        log.info("service 크루 구성원 데이터 삭제");

        // 크루 가입 신청 히스토리(CrewApplyHistory)에서 데이터 delete
        CrewApplyHistory crewApplyHistory = crewApplyHistoryCommendRepository.findCrewApplyHistoriesByCrewIdAndCumId(crewApplyDTO.getCrewId(), crewApplyDTO.getCumId());
        crewApplyHistoryCommendRepository.delete(crewApplyHistory);
        log.info("service 크루 가입 신청 히스토리 삭제");

        // 크루 구성원 히스토리(CrewMemberHistory) 테이블에서 데이터 delete
        CrewMemberHistory crewMemberHistory = crewMemberHistoryCommendRepository.findTopByCrewIdAndCumIdOrderByCrewMemberHistoryJoinDate(crewApplyDTO.getCrewId(), crewApplyDTO.getCumId());
        crewMemberHistoryCommendRepository.delete(crewMemberHistory);
        log.info("service 크루 구성원 히스토리 삭제");

        // 크루 가입 신청(CrewApply) 테이블에서 신청 데이터 다시 insert
        crewApply.setId(null);
        crewApplyCommendRepository.save(crewApply);
        log.info("service 가입신청 재삽입 완료");

    }

    @Override
    @Transactional
    public void crewApplyRejected(CrewApplyDTO crewApplyDTO) {
        // 크루 가입 신청 정보 검색
        CrewApply crewApply = crewApplyCommendRepository.findByCrewIdAndCumId(crewApplyDTO.getCrewId(), crewApplyDTO.getCumId());
        crewApplyDTO.setCrewApplyDate(crewApply.getCrewApplyDate());

        // 크루 가입 신청 히스토리 데이터 insert
        CrewApplyHistory crewApplyHistory = setCrewApplyHistoryInfo(crewApplyDTO, 'N');
        crewApplyHistoryCommendRepository.save(crewApplyHistory);

        // 크루 가입 신청 데이터 delete
        crewApplyCommendRepository.delete(crewApply);
    }

    @Override
    public void leaveCrew(CrewIdentifyMemberDTO crewIdentifyMemberDTO) {
        CrewMember crewMember = null;
        try {
            crewMember = leaveCrewTransaction(crewIdentifyMemberDTO);

            memberServiceClient.deleteMemberCrewInfo(crewIdentifyMemberDTO.getCrewId(), crewIdentifyMemberDTO.getCumId());
        } catch (Exception e) {
            if (crewMember == null) {
                log.info("service 크루 탈퇴 롤백 내용 없음");
                return;
            }

            leaveCrewRollBackTransaction(crewMember);
            log.info("크루 맴버 탈퇴 롤백 transaction 끝");
        }
    }

    @Transactional
    public CrewMember leaveCrewTransaction(CrewIdentifyMemberDTO crewIdentifyMemberDTO) {
        CrewMember crewMember = null;
        try {
            // 크루원 select
            crewMember = crewMemberCommendRepository.findByCrewIdAndCumId(crewIdentifyMemberDTO.getCrewId(), crewIdentifyMemberDTO.getCumId());

            // 크루원 히스토리 insert
            CrewMemberHistory crewMemberHistory = setCrewMemberQuitHistoryInfo(crewMember);
            crewMemberHistoryCommendRepository.save(crewMemberHistory);

            // 크루원 delete
            crewMemberCommendRepository.delete(crewMember);
        } catch (Exception e) {
            log.info("크루 탈퇴 transaction 예외 발생");
            crewMember = null;
        }
        return crewMember;
    }

    @Transactional
    public void leaveCrewRollBackTransaction(CrewMember crewMember) {
        // 크루원 히스토리 delete
        CrewMemberHistory crewMemberHistory = crewMemberHistoryCommendRepository.findTopByCrewIdAndCumIdOrderByCrewMemberHistoryStateUpdateDate(crewMember.getCrewId(), crewMember.getCumId());
        crewMemberHistoryCommendRepository.delete(crewMemberHistory);
        log.info("service 크루 탈퇴 기록 삭제");

        // 크루원 다시 insert
        crewMember.setId(null);
        crewMemberCommendRepository.save(crewMember);
        log.info("service 크루구성원 데이터 다시 삽입");
    }

    @Override
    @Transactional
    public void registCrewRole(String crewRole) {
        CrewMemberRole crewMemberRole = new CrewMemberRole();
        crewMemberRole.setCrewRoleName(crewRole);
        crewMemberRoleCommendRepository.save(crewMemberRole);
    }

    @Override
    @Transactional
    public void modifyCrewRole(CrewMemberAuthModifyDTO crewMemberAuthModifyDTO) {
        log.info("넘어온 권한 수정 dto : {}", crewMemberAuthModifyDTO);
        CrewMemberRole crewMemberRole = crewMemberRoleCommendRepository.findByCrewRoleName(crewMemberAuthModifyDTO.getCrewRoleOriginalName());
        CrewMemberAuth crewMemberAuth = crewMemberAuthCommendRepository.findByCrewRoleIdAndCrewRoleAuth(crewMemberRole.getId(), crewMemberAuthModifyDTO.getCrewRoleOriginalAuth());
        log.info("service crewMemberRole : {}", crewMemberRole);
        log.info("service crewMemberAuth : {}", crewMemberAuth);

        crewMemberRole.setCrewRoleName(crewMemberAuthModifyDTO.getCrewRoleName());
        crewMemberAuth.setCrewRoleAuth(crewMemberAuthModifyDTO.getCrewRoleAuth());
    }

    @Override
    @Transactional
    public void modifyCrewMemberRole(CrewMemberRoleModifyDTO crewMemberRoleModifyDTO) {
        if (crewMemberRoleModifyDTO == null) {
            log.info("입력된 권한이 없습니다.");
        }
        // 크루 구성원 정보 select
        CrewMember crewMember = crewMemberCommendRepository.findById(crewMemberRoleModifyDTO.getId()).orElse(null);

        // 크루 구성원 등급 update
        crewMember.setCrewRoleId(crewMemberRoleModifyDTO.getCrewRoleId());
        LocalDateTime now = LocalDateTime.now();
        crewMember.setCrewMemberRoleUpdateDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // 크루 구성원 히스토리 insert
        CrewMemberHistory crewMemberHistory = setCrewMemberRoleUpdateHistoryInfo(crewMember, crewMemberRoleModifyDTO, now);
        crewMemberHistoryCommendRepository.save(crewMemberHistory);

    }

    @Override
    public void banCrewMember(CrewMemberBanDTO crewMemberBanDTO) {
        CrewMember crewMember = null;

        try {
            crewMember = banCrewMemberTransaction(crewMemberBanDTO);

            memberServiceClient.deleteMemberCrewInfo(crewMemberBanDTO.getCrewId(), crewMemberBanDTO.getCumId());
        } catch (Exception e) {
            if (crewMember == null) {
                log.info("service 크루 강퇴 롤백 내용 없음");
                return;
            }

            banCrewMemberRollBackTransaction(crewMember);
            log.info("크루 맴버 강퇴 롤백 transaction 끝");
        }

    }

    @Transactional
    public CrewMember banCrewMemberTransaction(CrewMemberBanDTO crewMemberBanDTO) {
        CrewMember crewMember = null;
        try {
            // 크루원 정보 select
            crewMember = crewMemberCommendRepository.findById(crewMemberBanDTO.getId()).orElse(null);

            // 크루 구성원 히스토리에 insert
            CrewMemberHistory crewMemberHistory = setCrewMemberBanHistoryInfo(crewMember, crewMemberBanDTO.getBanReason());
            crewMemberHistoryCommendRepository.save(crewMemberHistory);

            // 크루 구성원 테이블에서 delete
            crewMemberCommendRepository.delete(crewMember);
        } catch (Exception e) {
            log.info("크루 강퇴 transaction 예외 발생");
            crewMember = null;
        }
        return crewMember;
    }

    @Transactional
    public void banCrewMemberRollBackTransaction(CrewMember crewMember) {
        // 크루원 히스토리 delete
        CrewMemberHistory crewMemberHistory = crewMemberHistoryCommendRepository.findTopByCrewIdAndCumIdOrderByCrewMemberHistoryStateUpdateDate(crewMember.getCrewId(), crewMember.getCumId());
        crewMemberHistoryCommendRepository.delete(crewMemberHistory);
        log.info("service 크루 강퇴 기록 삭제");

        // 크루원 다시 insert
        crewMember.setId(null);
        crewMemberCommendRepository.save(crewMember);
        log.info("service 크루구성원 데이터 다시 삽입");


    }


    // //////////////////////////////////////////////

    private static CrewMemberHistory setCrewMemberQuitHistoryInfo(CrewMember crewMember) {
        LocalDateTime now = LocalDateTime.now();
        CrewMemberHistory crewMemberHistory = new CrewMemberHistory();
        crewMemberHistory.setCrewRoleId(crewMember.getCrewRoleId());
        crewMemberHistory.setCrewId(crewMember.getCrewId());
        crewMemberHistory.setCrewMemberHistoryJoinDate(crewMember.getCrewMemberJoinDate());
        crewMemberHistory.setCrewMemberHistoryStateUpdateDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewMemberHistory.setCrewMemberHistoryState("QUITED");
        crewMemberHistory.setCrewMemberHistoryUpdateReason("크루 탈퇴");
        crewMemberHistory.setCumId(crewMember.getCumId());
        return crewMemberHistory;
    }

    private CrewMember setCrewMemberInfo(CrewApplyDTO crewApplyDTO) {
        LocalDateTime now = LocalDateTime.now();
        CrewMember crewMember = new CrewMember();

        crewMember.setCrewMemberJoinDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewMember.setCrewMemberRoleUpdateDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewMember.setCrewId(crewApplyDTO.getCrewId());
        crewMember.setCumId(crewApplyDTO.getCumId());
        crewMember.setCrewRoleId(1L);
        return crewMember;
    }

    private CrewMemberHistory setCrewMemberHistoryInfo(CrewApplyDTO crewApplyDTO, Long crewMemberRole) {
        CrewMemberHistory crewMemberHistory = new CrewMemberHistory();
        LocalDateTime now = LocalDateTime.now();

        crewMemberHistory.setCrewRoleId(crewMemberRole);
        crewMemberHistory.setCrewId(crewApplyDTO.getCrewId());
        crewMemberHistory.setCrewMemberHistoryJoinDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewMemberHistory.setCrewMemberHistoryStateUpdateDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewMemberHistory.setCrewMemberHistoryState("JOINED");
        crewMemberHistory.setCrewMemberHistoryUpdateReason("크루 가입");
        crewMemberHistory.setCumId(crewApplyDTO.getCumId());
        return crewMemberHistory;
    }

    private CrewApplyHistory setCrewApplyHistoryInfo(CrewApplyDTO crewApplyDTO, char isAccepted) {
        CrewApplyHistory crewApplyHistory = new CrewApplyHistory();
        LocalDateTime now = LocalDateTime.now();

        crewApplyHistory.setCrewId(crewApplyDTO.getCrewId());
        crewApplyHistory.setCrewApplyHistoryDate(crewApplyDTO.getCrewApplyDate());
        crewApplyHistory.setCrewApplyHistoryIsAccepted(isAccepted);
        crewApplyHistory.setCrewApplyHistoryAcceptDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewApplyHistory.setCumId(crewApplyDTO.getCumId());
        return crewApplyHistory;
    }

    private CrewMemberHistory setCrewMemberRoleUpdateHistoryInfo(CrewMember crewMember, CrewMemberRoleModifyDTO crewMemberRoleModifyDTO, LocalDateTime now) {
        CrewMemberHistory crewMemberHistory = new CrewMemberHistory();
        crewMemberHistory.setCrewRoleId(crewMemberRoleModifyDTO.getCrewRoleId());
        crewMemberHistory.setCrewId(crewMember.getCrewId());
        crewMemberHistory.setCrewMemberHistoryJoinDate(crewMember.getCrewMemberJoinDate());
        crewMemberHistory.setCrewMemberHistoryStateUpdateDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewMemberHistory.setCrewMemberHistoryState("ROLECHANGED");
        crewMemberHistory.setCrewMemberHistoryUpdateReason("등급 변경");
        crewMemberHistory.setCumId(crewMember.getCumId());
        return crewMemberHistory;
    }

    private CrewMemberHistory setCrewMemberBanHistoryInfo(CrewMember crewMember, String banReason) {
        CrewMemberHistory crewMemberHistory = new CrewMemberHistory();
        LocalDateTime now = LocalDateTime.now();

        crewMemberHistory.setCrewRoleId(crewMember.getCrewRoleId());
        crewMemberHistory.setCrewId(crewMember.getCrewId());
        crewMemberHistory.setCrewMemberHistoryJoinDate(crewMember.getCrewMemberJoinDate());
        crewMemberHistory.setCrewMemberHistoryStateUpdateDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        crewMemberHistory.setCrewMemberHistoryState("BANNED");
        crewMemberHistory.setCrewMemberHistoryUpdateReason(banReason);
        crewMemberHistory.setCumId(crewMember.getCumId());
        return crewMemberHistory;
    }

}
