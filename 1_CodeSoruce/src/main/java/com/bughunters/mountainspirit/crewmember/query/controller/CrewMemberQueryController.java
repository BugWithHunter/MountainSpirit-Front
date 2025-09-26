package com.bughunters.mountainspirit.crewmember.query.controller;

import com.bughunters.mountainspirit.crewmember.query.dto.CrewApplyListDTO;
import com.bughunters.mountainspirit.crewmember.query.service.CrewMemberQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/crew-apply")
@Slf4j
public class CrewMemberQueryController {
    private CrewMemberQueryService crewMemberQueryService;

    public CrewMemberQueryController(CrewMemberQueryService crewMemberQueryService) {
        this.crewMemberQueryService = crewMemberQueryService;
    }

    @GetMapping("/apply-list/{crewId}")
    public ResponseEntity<CrewApplyListDTO> crewApplyList(@PathVariable long crewId) {
        log.info("크루아이디 넘어온거 : {}", crewId);
        CrewApplyListDTO resultList = crewMemberQueryService.findAllCrewApplied(new CrewApplyListDTO(crewId,null));
        return ResponseEntity.ok().body(resultList);
    }
}
