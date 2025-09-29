package com.bughunters.mountainspirit.mountainbookmark.query.service;

import com.bughunters.mountainspirit.mountainbookmark.query.dto.CourseBookmarkDTO;
import com.bughunters.mountainspirit.mountainbookmark.query.dto.MountainBookmarkDTO;

import java.util.List;

public interface BookmarkService {
    List<MountainBookmarkDTO> getMountainBookmark(Long memberId);

    List<CourseBookmarkDTO> getCourseBookmark(Long memberId);
}
