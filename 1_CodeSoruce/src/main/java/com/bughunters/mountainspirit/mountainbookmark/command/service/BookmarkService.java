package com.bughunters.mountainspirit.mountainbookmark.command.service;

import com.bughunters.mountainspirit.mountainbookmark.command.dto.CourseBookmarkDTO;
import com.bughunters.mountainspirit.mountainbookmark.command.dto.MountainBookmarkDTO;
import com.bughunters.mountainspirit.mountainbookmark.command.entity.CourseBookmark;
import com.bughunters.mountainspirit.mountainbookmark.command.entity.MountainBookmark;
import com.bughunters.mountainspirit.mountainbookmark.command.repository.CourseBookmarkRepository;
import com.bughunters.mountainspirit.mountainbookmark.command.repository.MountainBookmarkRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookmarkService {
    private final MountainBookmarkRepository mountainBookmarkRepository;
    private final CourseBookmarkRepository courseBookmarkRepository;
    private final ModelMapper modelMapper;

    public BookmarkService(MountainBookmarkRepository mountainBookmarkRepository,
                           CourseBookmarkRepository courseBookmarkRepository, ModelMapper modelMapper) {
        this.mountainBookmarkRepository = mountainBookmarkRepository;
        this.courseBookmarkRepository = courseBookmarkRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void registMountain(MountainBookmarkDTO newBookmark) {   // insert - 산 북마크
        mountainBookmarkRepository.save(modelMapper.map(newBookmark, MountainBookmark.class));
    }

    @Transactional
    public void deleteMountain(long bookmarkCode) { // delete - 산 북마크
        mountainBookmarkRepository.deleteById(bookmarkCode);
    }

    @Transactional
    public void registCourse(CourseBookmarkDTO newBookmark) {   // insert - 코스 북마크
        courseBookmarkRepository.save(modelMapper.map(newBookmark, CourseBookmark.class));
    }

    @Transactional
    public void deleteCourse(long bookmarkCode) {   // delete - 코스 북마크
        courseBookmarkRepository.deleteById(bookmarkCode);
    }

}
