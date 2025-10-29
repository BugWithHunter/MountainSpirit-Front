package com.bughunters.mountainspirit.mountainbookmark.command.repository;

import com.bughunters.mountainspirit.mountainbookmark.command.entity.CourseBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseBookmarkRepository extends JpaRepository<CourseBookmark, Long> {
}
