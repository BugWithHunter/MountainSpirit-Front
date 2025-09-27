package com.bughunters.mountainspirit.crewclimbboard.command.controller;

import com.bughunters.mountainspirit.crewclimbboard.command.dto.CrewClimbBoardDTO;
import com.bughunters.mountainspirit.crewclimbboard.command.service.CrewClimbBoardCommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crew-climb-board")
@Slf4j
public class CrewClimbBoardCommendController {
    private CrewClimbBoardCommendService crewClimbBoardCommendService;

    @Autowired
    public CrewClimbBoardCommendController(CrewClimbBoardCommendService crewClimbBoardCommendService) {
        this.crewClimbBoardCommendService = crewClimbBoardCommendService;
    }

    @PostMapping("/climb-regist")
    public ResponseEntity<?> registCrewClimbBoard(@RequestBody CrewClimbBoardDTO crewClimbBoardDTO){
        crewClimbBoardCommendService.registCrewClimbBoard(crewClimbBoardDTO);
        return ResponseEntity.ok().build();
    }

}
