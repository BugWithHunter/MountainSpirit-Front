package com.bughunters.mountainspirit.mountain.query.controller;

import com.bughunters.mountainspirit.mountain.query.dto.CourseDTO;
import com.bughunters.mountainspirit.mountain.query.dto.MountainDTO;
import com.bughunters.mountainspirit.mountain.query.service.MountainSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController("MountainSearchController")
@RequestMapping("/mountain")
public class MountainController {
    private final MountainSearchService mountainSearchService;

    @Autowired
    public MountainController(MountainSearchService mountainSearchService) {
        this.mountainSearchService = mountainSearchService;
    }

    @GetMapping("/mountainall")    // 전체 산 조회
    public List<MountainDTO> getAllMountains(){
        List<MountainDTO> mountainList = mountainSearchService.getAllMountainInfo();
        return mountainList;
    }

    @GetMapping("/mountainsearch")  // localhost:8080/mountainsearch?mountainName=봉(부분검색 가능)
    public List<MountainDTO> getSearchMountain(@RequestParam String mountainName){
        List<MountainDTO> mountainSearchList = mountainSearchService.getSearchMountainInfo(mountainName);
        return mountainSearchList;
    }

    @GetMapping("/courseall")  // 전체 코스 조회
    public List<CourseDTO> getAllCourses(){
        List<CourseDTO> courseList = mountainSearchService.getAllCourseInfo();
        return courseList;
    }

    @GetMapping("/coursesearch")    // localhost:8080/coursesearch?mountainName=가야산(부분검색 불가능)
    public List<CourseDTO> getSearchCourse(@RequestParam String mountainName){
        List<CourseDTO> courseSearchList = mountainSearchService.getSearchCourseInfo(mountainName);
        return courseSearchList;
    }
}
