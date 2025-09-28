package com.bughunters.mountainspirit.crew.command.controller;

import com.bughunters.mountainspirit.crew.command.dto.CrewDeleteDTO;
import com.bughunters.mountainspirit.crew.command.dto.CrewModifyDTO;
import com.bughunters.mountainspirit.crew.command.dto.CrewRegistDTO;
import com.bughunters.mountainspirit.crew.command.service.CrewCommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/crew")
@Slf4j
public class CrewCommendController {
    private CrewCommendService crewCommendService;

    @Autowired
    public CrewCommendController(CrewCommendService crewCommendService) {
        this.crewCommendService = crewCommendService;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "I'm OK";
    }

    @PostMapping("/crew-create")
    public ResponseEntity<?> createCrew(@RequestBody CrewRegistDTO crewRegistDTO) {
        crewCommendService.insertCrew(crewRegistDTO);
        return ResponseEntity.created(URI.create("crew/crew_info")).build();
    }

    @PutMapping("/crew-modify")
    public ResponseEntity<?> modifyCrew(@RequestBody CrewModifyDTO crewModifyDTO) {
        crewCommendService.modifyCrew(crewModifyDTO);
        return ResponseEntity.created(URI.create("crew/crew_info/" + crewModifyDTO.getId())).build();
    }

    @PutMapping("/crew-delete")
    public ResponseEntity<?> deleteCrew(@RequestBody CrewDeleteDTO crewDeleteDTO) {
        crewCommendService.deleteCrew(crewDeleteDTO);
        return ResponseEntity.noContent().build();
    }
}
