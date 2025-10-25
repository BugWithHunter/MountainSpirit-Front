package com.bughunters.mountainspirit.mountain.query.controller;

import com.bughunters.mountainspirit.common.Pagination;
import com.bughunters.mountainspirit.common.PagingButtonInfo;
import com.bughunters.mountainspirit.mountain.query.dto.CourseDTO;
import com.bughunters.mountainspirit.mountain.query.dto.MountainDTO;
import com.bughunters.mountainspirit.mountain.query.service.MountainSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController("MountainSearchController")
@RequestMapping("/search")
public class MountainController {
    private final MountainSearchService mountainSearchService;

    @Autowired
    public MountainController(MountainSearchService mountainSearchService) {
        this.mountainSearchService = mountainSearchService;
    }

    @GetMapping("/mountain")    // 전체 산 조회
    public List<MountainDTO> getAllMountains(){
        List<MountainDTO> mountainList = mountainSearchService.getAllMountainInfo();
        return mountainList;
    }

    @GetMapping("/mountainList")    // 전체 산 조회
    public String getAllMountains(@PageableDefault(size=15)Pageable pageable, Model model){
        Page<MountainDTO> mountainList = mountainSearchService.getAllMountainInfo(pageable);
        PagingButtonInfo paging = Pagination.getPaginButtonInfo(mountainList);

        model.addAttribute("mountainList", mountainList);
        model.addAttribute("paging", paging);
//        List<MountainDTO> mountainList = mountainSearchService.getAllMountainInfo();
        return "search/mountainList";
//        return new MountainDTO(mountainList.getContent(), paging);
    }

    @GetMapping("/mountain/{mountainName}")  // localhost:8080/search/mountain/봉 (부분검색 가능)
    public List<MountainDTO> getSearchMountain(@PathVariable String mountainName){
        List<MountainDTO> mountainSearchList = mountainSearchService.getSearchMountainInfo(mountainName);
        return mountainSearchList;
    }

    @GetMapping("/course")  // 전체 코스 조회
    public List<CourseDTO> getAllCourses(){
        List<CourseDTO> courseList = mountainSearchService.getAllCourseInfo();
        return courseList;
    }

//    @GetMapping("/courseList")    // 전체 코스 조회
//    public String getAllCourses(@PageableDefault(size=15)Pageable pageable, Model model){
//        Page<CourseDTO> courseList = mountainSearchService.getAllCourseInfo(pageable);
//        PagingButtonInfo paging = Pagination.getPaginButtonInfo(courseList);
//
//        model.addAttribute("courseList", courseList);
//        model.addAttribute("paging", paging);
////        List<MountainDTO> mountainList = mountainSearchService.getAllMountainInfo();
//        return "search/courseList";
////        return new MountainDTO(mountainList.getContent(), paging);
//    }

    @GetMapping("/course/{mountainName}")    // localhost:8080/search/course/가야산 (부분검색 불가능)
    public List<CourseDTO> getSearchCourse(@PathVariable String mountainName){
        List<CourseDTO> courseSearchList = mountainSearchService.getSearchCourseInfo(mountainName);
        return courseSearchList;
    }
}
