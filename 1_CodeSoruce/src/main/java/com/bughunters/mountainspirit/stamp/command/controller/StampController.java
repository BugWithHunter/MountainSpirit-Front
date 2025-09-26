package com.bughunters.mountainspirit.stamp.command.controller;

import com.bughunters.mountainspirit.climbhistory.command.dto.RequestStartClimbMountainDTO;
import com.bughunters.mountainspirit.stamp.command.service.StampServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stamp")
public class StampController {
    StampServiceImpl stampService;

    @Autowired
    public StampController(StampServiceImpl stampService) {
        this.stampService = stampService;
    }

    @GetMapping("/test")
    public String test(@RequestBody RequestStartClimbMountainDTO request) {
        stampService.copleteClimbingMountain
                (request);


        return "";
    }
}
