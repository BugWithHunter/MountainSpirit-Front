package com.bughunters.mountainspirit.mountain.command.controller;

import com.bughunters.mountainspirit.mountain.command.dto.MountainDTO;
import com.bughunters.mountainspirit.mountain.command.service.MountainService;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
