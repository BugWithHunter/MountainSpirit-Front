package com.bughunters.mountainspirit.crewmember.command.controller;

import com.bughunters.mountainspirit.crewmember.command.dto.CrewApplyDTO;
import com.bughunters.mountainspirit.crewmember.command.service.CrewMemberCommendService;
import com.bughunters.mountainspirit.crewmember.query.service.CrewMemberQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@Controller
@RequestMapping("/crew-apply")
@Slf4j
public class CrewMemberCommendController {
    private CrewMemberCommendService crewMemberCommendService;
    private CrewMemberQueryService crewMemberQueryService;

    public CrewMemberCommendController(CrewMemberCommendService crewMemberCommendService) {
        this.crewMemberCommendService = crewMemberCommendService;
    }

    @PostMapping("/apply-request")
    public ResponseEntity<?> crewApplyRequest(@RequestBody CrewApplyDTO crewApplyDTO) {
        Integer forCheckBanDate = crewMemberQueryService.checkCrewApplyIsBanned(crewApplyDTO.getCrewId(),crewApplyDTO.getCumId());
        crewMemberCommendService.crewApplyRequest(crewApplyDTO);
        return ResponseEntity.ok().build();
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
}
