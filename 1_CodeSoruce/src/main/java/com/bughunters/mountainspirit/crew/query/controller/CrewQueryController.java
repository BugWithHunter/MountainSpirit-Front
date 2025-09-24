package com.bughunters.mountainspirit.crew.query.controller;

import com.bughunters.mountainspirit.crew.query.dto.CrewDTO;
import com.bughunters.mountainspirit.crew.query.service.CrewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/crew")
public class CrewQueryController {
    private CrewService crewService;

    @Autowired
    public CrewQueryController(CrewService crewService) {
        this.crewService = crewService;
    }

    @GetMapping
    public List<CrewDTO> findAllCrews(){
        List<CrewDTO> crews = crewService.findAllCrews();
        log.debug(crews.toString());
        return crews;
    }

    @GetMapping("/{crewId}")
    public CrewDTO findOneCrewById(@PathVariable("crewId") Long crewId){
        return crewService.findOneCrewById(crewId);
    }
}
