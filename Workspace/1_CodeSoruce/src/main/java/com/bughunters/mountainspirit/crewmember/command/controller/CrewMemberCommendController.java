package com.bughunters.mountainspirit.crewmember.command.controller;

import com.bughunters.mountainspirit.crewmember.command.dto.*;
import com.bughunters.mountainspirit.crewmember.command.service.CrewMemberCommendService;
import com.bughunters.mountainspirit.crewmember.query.service.CrewMemberQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/crew-member")
@Slf4j
public class CrewMemberCommendController {
    private CrewMemberCommendService crewMemberCommendService;
    private CrewMemberQueryService crewMemberQueryService;

    @Autowired
    public CrewMemberCommendController(CrewMemberCommendService crewMemberCommendService,
                                       CrewMemberQueryService crewMemberQueryService) {
        this.crewMemberCommendService = crewMemberCommendService;
        this.crewMemberQueryService = crewMemberQueryService;
    }

    @PostMapping("/apply-request")
    public ResponseEntity<?> crewApplyRequest(@RequestBody CrewApplyDTO crewApplyDTO) {
        boolean forCheckBanDate = crewMemberQueryService.checkCrewApplyIsBanned(crewApplyDTO.getCrewId(), crewApplyDTO.getCumId());
        if (forCheckBanDate == true) {
            log.info("추방당한 회원입니다.");
            return ResponseEntity.ok().body(new CrewApplyResultDTO("추방당한 회원입니다."));
        }
        boolean flag = crewMemberCommendService.crewApplyRequest(crewApplyDTO);
        return ResponseEntity.ok().body(flag ? new CrewApplyResultDTO("가입신청이 완료되었습니다.") : new CrewApplyResultDTO("이미 가입신청이 되어있습니다."));
    }

    @PostMapping("/apply-cancel")
    public ResponseEntity<?> crewApplyCancel(@RequestBody CrewApplyDTO crewApplyDTO) {
        crewMemberCommendService.crewApplyCancel(crewApplyDTO);
        return ResponseEntity.created(URI.create("")).build();
    }

    @PostMapping("/apply-approved")
    public ResponseEntity<?> crewApplyApproved(@RequestBody CrewApplyDTO crewApplyDTO) {
        crewMemberCommendService.registCrewMemberByCrewApplyApprove(crewApplyDTO);
        return ResponseEntity.created(URI.create("")).build();
    }

    @PostMapping("/apply-reject")
    public ResponseEntity<?> crewApplyRejected(@RequestBody CrewApplyDTO crewApplyDTO) {
        crewMemberCommendService.crewApplyRejected(crewApplyDTO);
        return ResponseEntity.created(URI.create("")).build();
    }

    @PostMapping("/leave-crew")
    public ResponseEntity<?> leaveCrew(@RequestBody CrewIdentifyMemberDTO crewIdentifyMemberDTO) {
        // 크루장이면 탈퇴 못하게 막는 기능도 필요
        crewMemberCommendService.leaveCrew(crewIdentifyMemberDTO);
        return ResponseEntity.ok().build();
    }

    // //////////////////////////관리자 권한 기능//////////////////////////
    @PostMapping("/regist-crew-role")
    public ResponseEntity<?> registCrewRole(@RequestBody String crewRole) {
        crewMemberCommendService.registCrewRole(crewRole);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/modify-crew-role")
    public ResponseEntity<?> modifyCrewRole(@RequestBody CrewMemberAuthModifyDTO crewMemberAuthModifyDTO) {
        crewMemberCommendService.modifyCrewRole(crewMemberAuthModifyDTO);
        return ResponseEntity.ok().build();
    }

    // //////////////////////////크루장 권한 기능//////////////////////////
    @PostMapping("/modify-crew-member-role")
    public ResponseEntity<?> modifyCrewMemberRole(@RequestBody CrewMemberRoleModifyDTO crewMemberRoleModifyDTO) {
        crewMemberCommendService.modifyCrewMemberRole(crewMemberRoleModifyDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/ban-crew-member")
    public ResponseEntity<?> banCrewMember(@RequestBody CrewMemberBanDTO crewMemberBanDTO) {
        crewMemberCommendService.banCrewMember(crewMemberBanDTO);
        return ResponseEntity.ok().build();
    }
}
