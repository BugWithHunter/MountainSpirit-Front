package com.bughunters.mountainspirit.mountainreview.query.service;

import com.bughunters.mountainspirit.mountainreview.query.dto.MountainReviewDTO;

import java.util.List;

public interface MountainReviewService {
    List<MountainReviewDTO> getMountainReview(String mountainName);
}
