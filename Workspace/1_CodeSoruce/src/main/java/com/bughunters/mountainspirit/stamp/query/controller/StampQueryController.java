package com.bughunters.mountainspirit.stamp.query.controller;

import com.bughunters.mountainspirit.stamp.query.dto.CourseStampDTO;
import com.bughunters.mountainspirit.stamp.query.dto.MountainStampDTO;
import com.bughunters.mountainspirit.stamp.query.service.StampService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stamp")
public class StampQueryController {
    private final StampService stampService;

    public StampQueryController(StampService stampService) {
        this.stampService = stampService;
    }

    @GetMapping("/mountain/{memberId}") // 산 도장 조회 localhost:8080/stamp/mountain/195
    public List<MountainStampDTO> getMountainStampList(@PathVariable long memberId){
        List<MountainStampDTO> mountainStampList = stampService.getMountainStamp(memberId);
        return mountainStampList;
    }

    @GetMapping("/course/{memberId}")   // 코스 도장 조회 localhost:8080/stamp/course/195
    public List<CourseStampDTO> getCourseStampList(@PathVariable long memberId){
        List<CourseStampDTO> courseStampList = stampService.getCourseStamp(memberId);
        return courseStampList;
    }
}
