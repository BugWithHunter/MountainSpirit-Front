package com.bughunters.mountainspirit.crewclimbboard.command.controller;

import com.bughunters.mountainspirit.crewclimbboard.command.dto.CrewClimbBoardApplyDTO;
import com.bughunters.mountainspirit.crewclimbboard.command.dto.CrewClimbBoardCancelDTO;
import com.bughunters.mountainspirit.crewclimbboard.command.dto.CrewClimbBoardDTO;
import com.bughunters.mountainspirit.crewclimbboard.command.dto.CrewClimbRecordRegistDTO;
import com.bughunters.mountainspirit.crewclimbboard.command.service.CrewClimbBoardCommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/climb-modify")
    public ResponseEntity<?> modifyCrewClimbBoard(@RequestBody CrewClimbBoardDTO crewClimbBoardDTO){
        crewClimbBoardCommendService.modifyCrewClimbBoard(crewClimbBoardDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/climb-delete/{id}")
    public ResponseEntity<?> deleteCrewClimbBoardById(@PathVariable Long id){
        crewClimbBoardCommendService.deleteCrewClimbBoardById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/climb-close-apply/{id}")
    public ResponseEntity<?> closeCrewClimbBoardById(@PathVariable Long id){
        crewClimbBoardCommendService.closeCrewClimbBoardById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/climb-apply")
    public ResponseEntity<?> applyCrewClimbBoard(@RequestBody CrewClimbBoardApplyDTO crewClimbBoardApplyDTO){
        crewClimbBoardCommendService.applyCrewClimbBoard(crewClimbBoardApplyDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/climb-cancel")
    public ResponseEntity<?> cancelCrewClimb(@RequestBody CrewClimbBoardCancelDTO crewClimbBoardCancelDTO){
        crewClimbBoardCommendService.cancelCrewClimb(crewClimbBoardCancelDTO);
        return ResponseEntity.ok().build();
    }


}
