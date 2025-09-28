package com.bughunters.mountainspirit.mountainreview.command.controller;

import com.bughunters.mountainspirit.mountain.command.service.MountainService;
import com.bughunters.mountainspirit.mountainreview.command.dto.CourseReviewDTO;
import com.bughunters.mountainspirit.mountainreview.command.dto.MountainReviewDTO;
import com.bughunters.mountainspirit.mountainreview.command.entity.CourseReview;
import com.bughunters.mountainspirit.mountainreview.command.service.MountainReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController("MountainReviewCommandController")
@RequestMapping("/mountainreview")
public class MountainReviewController {
    private final MountainReviewService mountainReviewService;
    private final MountainService mountainService;

    @Autowired
    public MountainReviewController(MountainReviewService mountainReviewService, MountainService mountainService) {
        this.mountainReviewService = mountainReviewService;
        this.mountainService = mountainService;
    }

    @GetMapping("/registMountain")
    public void registMountain(){}

    @PostMapping("/registMountain")
    public String registMountain(@RequestBody MountainReviewDTO newReview){
        mountainReviewService.registMountain(newReview);
        String frtrlNm = mountainService.getMountainName(newReview.getFrtrlId());
        return "/redirect:/mountainreview/review?mountainName=" + frtrlNm;
    }

    @GetMapping("/modifyMountain")
    public void modifyMountain(){}

    @PostMapping("/modifyMountain")
    public String modifyMountain(@RequestBody MountainReviewDTO modifyReview){
        mountainReviewService.modifyMountain(modifyReview);
        return "redirect:/mountainreview/" + modifyReview.getId();
    }

    @GetMapping("/deleteMountain")
    public void deleteMountain(){}

    @PostMapping("/deleteMountain")
    public String deleteMountain(@RequestParam long reviewCode){
        mountainReviewService.deleteMountain(reviewCode);
        return "삭제 완료";
    }

    @GetMapping("/registCourse")
    public void registCourse(){}

    @PostMapping("/registCourse")
    public String registCourse(@RequestBody CourseReviewDTO newReview){
        mountainReviewService.registCourse(newReview);
        return "코스 후기 등록 완료";
    }

    @GetMapping("/modifyCourse")
    public void modifyCourse(){}

    @PostMapping("/modifyCourse")
    public String modifyCourse(@RequestBody CourseReviewDTO modifyReview){
        mountainReviewService.modifyCourse(modifyReview);
        return "redirect:/mountainreview/" + modifyReview.getId();
    }

    @GetMapping("/deleteCourse")
    public void deleteCourse(){}

    @PostMapping("/deleteCourse")
    public String deleteCourse(@RequestParam long reviewCode){
        mountainReviewService.deleteCourse(reviewCode);
        return "삭제 완료";
    }
}
