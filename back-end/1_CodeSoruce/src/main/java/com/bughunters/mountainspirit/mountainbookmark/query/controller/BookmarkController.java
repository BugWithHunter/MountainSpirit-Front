package com.bughunters.mountainspirit.mountainbookmark.query.controller;

import com.bughunters.mountainspirit.mountainbookmark.query.dto.CourseBookmarkDTO;
import com.bughunters.mountainspirit.mountainbookmark.query.dto.MountainBookmarkDTO;
import com.bughunters.mountainspirit.mountainbookmark.query.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("BookmarkQueryController")
@RequestMapping("/bookmark")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @Autowired
    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @GetMapping("/mountain/{memberId}")
    public List<MountainBookmarkDTO> getMountainBookmarkList(@PathVariable Long memberId){
        List<MountainBookmarkDTO> mountainBookmarkList = bookmarkService.getMountainBookmark(memberId);
        return mountainBookmarkList;
    }

    @GetMapping("/course/{memberId}")
    public List<CourseBookmarkDTO> getCourseBookmarkList(@PathVariable Long memberId){
        List<CourseBookmarkDTO> courseBookmarkList = bookmarkService.getCourseBookmark(memberId);
        return courseBookmarkList;
    }

}
