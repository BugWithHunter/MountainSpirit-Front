package com.bughunters.mountainspirit.mountainreview.query.controller;

import com.bughunters.mountainspirit.mountainreview.query.dto.CourseReviewDTO;
import com.bughunters.mountainspirit.mountainreview.query.service.MountainReviewService;
import com.bughunters.mountainspirit.mountainreview.query.dto.MountainReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("MountainReviewQueryController")
@RequestMapping("/review")
public class MountainReviewController {
    private final MountainReviewService mountainReviewService;

    @Autowired
    public MountainReviewController(MountainReviewService mountainReviewService) {
        this.mountainReviewService = mountainReviewService;
    }

    @GetMapping("/mountain/{mountainName}")   // 산 이름 정확히 일치해야함
    public List<MountainReviewDTO> getMountainReviewList(@PathVariable String mountainName){
        List<MountainReviewDTO> mountainReviewList = mountainReviewService.getMountainReview(mountainName);
        return mountainReviewList;
    }

    @GetMapping("/course/{courseName}")  // 코스 이름 정확히 일치해야함
    public List<CourseReviewDTO> getCourseReviewList(@PathVariable String courseName){
        List<CourseReviewDTO> courseReviewList = mountainReviewService.getCourseReview(courseName);
        return courseReviewList;
    }
}
