package com.bughunters.mountainspirit.member.query.controller;

import com.bughunters.mountainspirit.member.query.dto.FindClimbCheckQueryDTO;
import com.bughunters.mountainspirit.member.query.dto.RequestStartClimbMountainQueryDTO;
import com.bughunters.mountainspirit.member.query.service.MemberQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberQueryController {

    private final MemberQueryService memberQueryService;

    @Autowired
    public MemberQueryController(MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }

    @GetMapping("/checkClimbing")
    public ResponseEntity<FindClimbCheckQueryDTO> findClimbCheckByMember(@RequestBody RequestStartClimbMountainQueryDTO request) {

        FindClimbCheckQueryDTO findClimbCheckDTO = memberQueryService.findClimbCheckByMember(request);
        return ResponseEntity.ok()
                .body(findClimbCheckDTO);
    }



}
