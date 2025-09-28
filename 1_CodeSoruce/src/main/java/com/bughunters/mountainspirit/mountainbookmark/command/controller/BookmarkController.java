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

    @GetMapping("/registMountain")
    public void registMountain(){}

    @PostMapping("/registMountain")
    public String registMountain(@RequestBody MountainBookmarkDTO newBookmark){
        bookmarkService.registMountain(newBookmark);
        return "북마크 추가 완료";
    }

    @GetMapping("/deleteMountain")
    public void deleteMountain(){}

    @PostMapping("/deleteMountain")
    public String deleteMountain(@RequestParam long bookmarkCode){
        bookmarkService.deleteMountain(bookmarkCode);
        return "북마크 삭제 완료";
    }

    @GetMapping("/registCourse")
    public void registCourse(){}

    @PostMapping("/registCourse")
    public String registCourse(@RequestBody CourseBookmarkDTO newBookmark){
        bookmarkService.registCourse(newBookmark);
        return "북마크 추가 완료";
    }

    @GetMapping("/deleteCourse")
    public void deleteCourse(){}

    @PostMapping("/deleteCourse")
    public String deleteCourse(@RequestParam long bookmarkCode){
        bookmarkService.deleteCourse(bookmarkCode);
        return "북마크 삭제 완료";
    }
}
