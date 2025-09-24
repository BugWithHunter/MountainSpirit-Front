package com.bughunters.mountainspirit.crew.command.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrewCommendController {
    @GetMapping("/health")
    public String healthCheck(){
        return "I'm OK";
    }
}
