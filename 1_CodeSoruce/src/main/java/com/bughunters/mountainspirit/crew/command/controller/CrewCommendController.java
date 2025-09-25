package com.bughunters.mountainspirit.crew.command.controller;

import com.bughunters.mountainspirit.crew.command.entity.Crew;
import com.bughunters.mountainspirit.crew.command.service.CrewCommendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrewCommendController {
    private CrewCommendService crewCommendService;

    @GetMapping("/health")
    public String healthCheck(){
        return "I'm OK";
    }

    @PostMapping("/crew")
    public CrewDTO createCrew(@RequestBody Crew crew){
        return crewCommendService.insertCrew(crew);
    }
}
