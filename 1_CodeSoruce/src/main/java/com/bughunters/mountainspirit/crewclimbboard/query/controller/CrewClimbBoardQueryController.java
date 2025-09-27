package com.bughunters.mountainspirit.crewclimbboard.query.controller;

import com.bughunters.mountainspirit.crewclimbboard.query.service.CrewClimbBoardQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/crew-climb-board")
public class CrewClimbBoardQueryController {
    private CrewClimbBoardQueryService crewClimbBoardQueryService;

    @Autowired
    public CrewClimbBoardQueryController(CrewClimbBoardQueryService crewClimbBoardQueryService){
        this.crewClimbBoardQueryService = crewClimbBoardQueryService;
    }
}
