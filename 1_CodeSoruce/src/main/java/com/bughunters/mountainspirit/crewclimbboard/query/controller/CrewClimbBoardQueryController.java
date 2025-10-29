package com.bughunters.mountainspirit.crewclimbboard.query.controller;

import com.bughunters.mountainspirit.crewclimbboard.query.dto.CrewClimbBoardAndMountainAndCrewMemberDTO;
import com.bughunters.mountainspirit.crewclimbboard.query.dto.CrewClimbBoardAndMountainDTO;
import com.bughunters.mountainspirit.crewclimbboard.query.dto.MyCrewClimbBoardListDTO;
import com.bughunters.mountainspirit.crewclimbboard.query.service.CrewClimbBoardQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/crew-climb-board")
public class CrewClimbBoardQueryController {
    private CrewClimbBoardQueryService crewClimbBoardQueryService;

    @Autowired
    public CrewClimbBoardQueryController(CrewClimbBoardQueryService crewClimbBoardQueryService){
        this.crewClimbBoardQueryService = crewClimbBoardQueryService;
    }

    @GetMapping("/climb-board-list/{crewId}")
    public ResponseEntity<List<CrewClimbBoardAndMountainDTO>> findAllCrewClimbBoardByCrewId(@PathVariable Long crewId){
        List<CrewClimbBoardAndMountainDTO> crewClimbBoardAndMountainDTO = crewClimbBoardQueryService.findAllCrewClimbBoardByCrewId(crewId);
        return ResponseEntity.ok().body(crewClimbBoardAndMountainDTO);
    }

    @GetMapping("/climb-board/{crewClimbBoardId}")
    public ResponseEntity<CrewClimbBoardAndMountainAndCrewMemberDTO> findOneCrewClimbBoardByCrewClimbBoardId(@PathVariable Long crewClimbBoardId){
        CrewClimbBoardAndMountainAndCrewMemberDTO crewClimbBoardAndMountainAndCrewMemberDTO = crewClimbBoardQueryService.findOneCrewClimbBoardByCrewClimbBoardId(crewClimbBoardId);
        return ResponseEntity.ok().body(crewClimbBoardAndMountainAndCrewMemberDTO);
    }

    @GetMapping("/climb-board-mylist/{memberId}")
    public ResponseEntity<List<MyCrewClimbBoardListDTO>> findCrewClimbBoardByCrewMemberId(@PathVariable Long memberId){
        List<MyCrewClimbBoardListDTO> myCrewClimbBoardListDTO = crewClimbBoardQueryService.findMyCrewClimbBoardByMemberId(memberId);
        return ResponseEntity.ok().body(myCrewClimbBoardListDTO);
    }

}
