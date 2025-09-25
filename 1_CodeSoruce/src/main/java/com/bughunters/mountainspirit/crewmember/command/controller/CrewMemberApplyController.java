package com.bughunters.mountainspirit.crewmember.command.controller;

import com.bughunters.mountainspirit.crewmember.command.dto.CrewApplyDTO;
import com.bughunters.mountainspirit.crewmember.command.service.CrewMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/crew-apply")
@Slf4j
public class CrewMemberApplyController {
    private CrewMemberService crewMemberService;

    public CrewMemberApplyController(CrewMemberService crewMemberService) {
        this.crewMemberService = crewMemberService;
    }

    @PostMapping("/apply-request")
    public ResponseEntity<?> crewApplyRequest(@RequestBody CrewApplyDTO crewApplyDTO) {
        crewMemberService.crewApplyRequest(crewApplyDTO);
        return ResponseEntity.ok().build();
    }
}
