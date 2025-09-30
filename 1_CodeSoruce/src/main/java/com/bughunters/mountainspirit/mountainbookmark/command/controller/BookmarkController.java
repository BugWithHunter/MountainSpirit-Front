package com.bughunters.mountainspirit.mountainbookmark.command.controller;

import com.bughunters.mountainspirit.mountainbookmark.command.dto.CourseBookmarkDTO;
import com.bughunters.mountainspirit.mountainbookmark.command.dto.MountainBookmarkDTO;
import com.bughunters.mountainspirit.mountainbookmark.command.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("BookmarkCommandController")
@RequestMapping("/bookmark")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @Autowired
    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping("/mountain")
    public String registMountainBookmark(@RequestBody MountainBookmarkDTO newBookmark){
        bookmarkService.registMountain(newBookmark);
        return "북마크 추가 완료";
    }

    @DeleteMapping("/mountain/{bookmarkCode}")
    public String deleteMountainBookmark(@PathVariable long bookmarkCode){
        bookmarkService.deleteMountain(bookmarkCode);
        return "북마크 삭제 완료";
    }

    @PostMapping("/course")
    public String registCourseBookmark(@RequestBody CourseBookmarkDTO newBookmark){
        bookmarkService.registCourse(newBookmark);
        return "북마크 추가 완료";
    }

    @DeleteMapping("/course/{bookmarkCode}")
    public String deleteCourse(@PathVariable long bookmarkCode){
        bookmarkService.deleteCourse(bookmarkCode);
        return "북마크 삭제 완료";
    }
}
