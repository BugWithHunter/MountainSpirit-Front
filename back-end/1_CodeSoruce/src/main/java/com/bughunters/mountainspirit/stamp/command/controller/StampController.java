package com.bughunters.mountainspirit.stamp.command.controller;

import com.bughunters.mountainspirit.stamp.command.dto.RequestSubmmitClimbMountainDTO;
import com.bughunters.mountainspirit.stamp.command.dto.ResponseCourseDTO;
import com.bughunters.mountainspirit.stamp.command.entity.CourseStamp;
import com.bughunters.mountainspirit.stamp.command.service.StampService;
import com.bughunters.mountainspirit.stamp.command.service.StampServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stamp")
public class StampController {
    StampService stampService;

    @Autowired
    public StampController(StampServiceImpl stampService) {
        this.stampService = stampService;
    }

    @GetMapping("/test")
    public String test(@RequestBody RequestSubmmitClimbMountainDTO request) {

        stampService.copleteClimbingMountain(request);


        return "";
    }

    @GetMapping("/corse-stamp/{memberId}")
    public ResponseEntity<List<CourseStamp>> findCourseStamp(@PathVariable Long memberId){
        List<CourseStamp> stamps = stampService.findCourseStamp(memberId);

        return ResponseEntity.ok()
                .body(stamps);
    }
}
