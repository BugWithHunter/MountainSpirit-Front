package com.bughunters.mountainspirit.climbhistory.query.controller;

import com.bughunters.mountainspirit.climbhistory.query.dto.FindClimbCheckQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.dto.RequestStartClimbMountainQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.service.ClimbHistoryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/climb-history")
public class ClimbHistoryQueryController {

    private final ClimbHistoryQueryService climbHistoryQueryService;
    private final ReactorClientHttpConnector connector;

    @Autowired
    public ClimbHistoryQueryController(ClimbHistoryQueryService climbHistoryQueryService, ReactorClientHttpConnector connector) {
        this.climbHistoryQueryService = climbHistoryQueryService;
        this.connector = connector;
    }

    @GetMapping("/climbing")
    public ResponseEntity<FindClimbCheckQueryDTO> findClimbCheckByMember(@RequestBody RequestStartClimbMountainQueryDTO request) {

        FindClimbCheckQueryDTO findClimbCheckDTO = climbHistoryQueryService.findClimbCheckByMember(request);
        return ResponseEntity.ok()
                .body(findClimbCheckDTO);
    }

    @GetMapping("/climbing-by-status")
    public ResponseEntity<List<RequestStartClimbMountainQueryDTO>> findClimbNotComplete(
            @RequestParam Long  userId,
            @RequestParam String status){
        System.out.println(userId);
        System.out.println(status);

        RequestStartClimbMountainQueryDTO request =
                new RequestStartClimbMountainQueryDTO(userId, "","", status);
        List<RequestStartClimbMountainQueryDTO> findClimbCheckDTO
                = climbHistoryQueryService.findClimbNotComplete(request);
        return ResponseEntity.ok()
                .body(findClimbCheckDTO);
    }



}
