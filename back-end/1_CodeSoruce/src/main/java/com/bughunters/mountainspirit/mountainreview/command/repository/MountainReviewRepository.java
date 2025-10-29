package com.bughunters.mountainspirit.mountainreview.command.repository;

import com.bughunters.mountainspirit.mountainreview.command.entity.MountainReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MountainReviewRepository extends JpaRepository<MountainReview, Long> {

}