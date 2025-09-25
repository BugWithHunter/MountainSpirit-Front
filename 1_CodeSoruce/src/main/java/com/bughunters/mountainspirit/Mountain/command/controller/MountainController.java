package com.bughunters.mountainspirit.Mountain.command.controller;

import com.bughunters.mountainspirit.Mountain.command.service.MountainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/mountain")
public class MountainController {

    private final MountainService mountainService;

    public MountainController(MountainService mountainService) {
        this.mountainService = mountainService;
    }

    @PutMapping("/mountains")
    public void mergeAllMountains() {
        mountainService.mergeAllMountains();
    }

    @PutMapping("/courses")
    public void mergeAllCourses() {
        mountainService.mergeAllCourses();
    }
}
