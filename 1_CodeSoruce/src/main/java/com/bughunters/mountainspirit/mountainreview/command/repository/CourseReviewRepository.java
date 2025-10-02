package com.bughunters.mountainspirit.mountainreview.command.repository;

import com.bughunters.mountainspirit.mountainreview.command.entity.CourseReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseReviewRepository extends JpaRepository<CourseReview, Long> {
}
