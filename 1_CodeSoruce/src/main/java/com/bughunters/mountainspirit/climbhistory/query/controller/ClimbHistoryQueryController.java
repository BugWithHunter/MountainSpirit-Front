package com.bughunters.mountainspirit.climbhistory.query.controller;

import com.bughunters.mountainspirit.climbhistory.query.dto.FindClimbCheckQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.dto.RequestStartClimbMountainQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.service.ClimbHistoryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/climb-history")
public class ClimbHistoryQueryController {

    private final ClimbHistoryQueryService climbHistoryQueryService;

    @Autowired
    public ClimbHistoryQueryController(ClimbHistoryQueryService climbHistoryQueryService) {
        this.climbHistoryQueryService = climbHistoryQueryService;
    }

    @GetMapping("/climbing")
    public ResponseEntity<FindClimbCheckQueryDTO> findClimbCheckByMember(@RequestBody RequestStartClimbMountainQueryDTO request) {

        FindClimbCheckQueryDTO findClimbCheckDTO = climbHistoryQueryService.findClimbCheckByMember(request);
        return ResponseEntity.ok()
                .body(findClimbCheckDTO);
    }



}
