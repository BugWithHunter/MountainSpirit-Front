package com.bughunters.mountainspirit.crew.command.controller;

import com.bughunters.mountainspirit.crew.command.dto.CrewDeleteDTO;
import com.bughunters.mountainspirit.crew.command.dto.CrewModifyDTO;
import com.bughunters.mountainspirit.crew.command.dto.CrewRegistDTO;
import com.bughunters.mountainspirit.crew.command.entity.Crew;
import com.bughunters.mountainspirit.crew.command.service.CrewCommendService;
import com.bughunters.mountainspirit.crew.command.service.CrewCommendServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/crew")
@Slf4j
public class CrewCommendController {
    private CrewCommendServiceInter crewCommendServiceInter;

    @Autowired
    public CrewCommendController(CrewCommendServiceInter crewCommendServiceInter) {
        this.crewCommendServiceInter = crewCommendServiceInter;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "I'm OK";
    }

    @PostMapping("/crew_create")
    public ResponseEntity<?> createCrew(@RequestBody CrewRegistDTO crewRegistDTO) {
        crewCommendServiceInter.insertCrew(crewRegistDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/crew_modify")
    public ResponseEntity<?> modifyCrew(@RequestBody CrewModifyDTO crewModifyDTO) {
        crewCommendServiceInter.modifyCrew(crewModifyDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/crew_delete")
    public ResponseEntity<?> deleteCrew(@RequestBody CrewDeleteDTO crewDeleteDTO) {
        crewCommendServiceInter.deleteCrew(crewDeleteDTO);
        return ResponseEntity.noContent().build();
    }
}
