package com.bughunters.mountainspirit.mountain.command.controller;

import com.bughunters.mountainspirit.mountain.command.service.MountainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mountain")
public class MountainController {
    private final MountainService mountainService;

    @Autowired
    public MountainController(MountainService mountainService) {
        this.mountainService = mountainService;
    }

}
