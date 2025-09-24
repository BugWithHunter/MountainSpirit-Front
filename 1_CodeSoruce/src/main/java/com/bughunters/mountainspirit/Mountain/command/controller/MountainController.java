package com.bughunters.mountainspirit.Mountain.command.controller;

import com.bughunters.mountainspirit.Mountain.command.dto.ResponseApiMountainDTO;
import com.bughunters.mountainspirit.Mountain.command.entity.Mountain;
import com.bughunters.mountainspirit.Mountain.command.service.MountainService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mountain")
public class MountainController {

    private final MountainService mountainService;

    public MountainController(MountainService mountainService) {
        this.mountainService = mountainService;
    }

    @GetMapping("/test1234")
    public void test1234() {
//        System.out.println(mountainRepository.findAll());
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
