package com.bughunters.mountainspirit.mountainreview.query.controller;

import com.bughunters.mountainspirit.mountainreview.query.service.MountainReviewService;
import com.bughunters.mountainspirit.mountainreview.query.dto.MountainReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mountainreview")
public class MountainReviewController {
    private final MountainReviewService mountainReviewService;

    @Autowired
    public MountainReviewController(MountainReviewService mountainReviewService) {
        this.mountainReviewService = mountainReviewService;
    }

    @GetMapping("/review")   // localhost:8080/mountain/review?mountainName=가야산(부분검색 불가능)
    public List<MountainReviewDTO> getMountainReviewList(@RequestParam String mountainName){
        List<MountainReviewDTO> mountainReviewList = mountainReviewService.getMountainReview(mountainName);
        return mountainReviewList;
    }
}
