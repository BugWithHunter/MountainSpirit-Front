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
@RequestMapping("/review")
public class MountainReviewController {
    private final MountainReviewService mountainReviewService;
    private final MountainService mountainService;

    @Autowired
    public MountainReviewController(MountainReviewService mountainReviewService, MountainService mountainService) {
        this.mountainReviewService = mountainReviewService;
        this.mountainService = mountainService;
    }

    @PostMapping("/mountain")   // insert - 산 후기
    public String registMountain(@RequestBody MountainReviewDTO newReview){
        mountainReviewService.registMountain(newReview);
//        String frtrlNm = mountainService.getMountainName(newReview.getFrtrlId());
        return "산 후기 등록 성공";
    }

    @PutMapping("/mountain")    // update - 산 후기
    public String modifyMountain(@RequestBody MountainReviewDTO modifyReview){
        mountainReviewService.modifyMountain(modifyReview);
        return "산 후기 수정 성공";
    }

    @DeleteMapping("/mountain/{reviewCode}")    // delete - 산 후기
    public String deleteMountain(@PathVariable long reviewCode){
        mountainReviewService.deleteMountain(reviewCode);
        return "삭제 완료";
    }

    @PostMapping("/course") // insert - 코스 후기
    public String registCourse(@RequestBody CourseReviewDTO newReview){
        mountainReviewService.registCourse(newReview);
        return "코스 후기 등록 완료";
    }

    @PutMapping("/course")  // update - 코스 후기
    public String modifyCourse(@RequestBody CourseReviewDTO modifyReview){
        mountainReviewService.modifyCourse(modifyReview);
        return "코스 후기 수정";
    }

    @DeleteMapping("/course/{reviewCode}")  // delete - 코스 후기
    public String deleteCourse(@PathVariable long reviewCode){
        mountainReviewService.deleteCourse(reviewCode);
        return "삭제 완료";
    }
}
