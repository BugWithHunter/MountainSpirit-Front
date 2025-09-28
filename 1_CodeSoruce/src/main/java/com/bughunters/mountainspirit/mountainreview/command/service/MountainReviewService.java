package com.bughunters.mountainspirit.mountainreview.command.service;

import com.bughunters.mountainspirit.mountain.command.dto.MountainDTO;
import com.bughunters.mountainspirit.mountain.command.entity.Mountain;
import com.bughunters.mountainspirit.mountainreview.command.dto.CourseReviewDTO;
import com.bughunters.mountainspirit.mountainreview.command.dto.MountainReviewDTO;
import com.bughunters.mountainspirit.mountainreview.command.entity.CourseReview;
import com.bughunters.mountainspirit.mountainreview.command.entity.MountainReview;
import com.bughunters.mountainspirit.mountainreview.command.repository.CourseReviewRepository;
import com.bughunters.mountainspirit.mountainreview.command.repository.MountainReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MountainReviewService {
    private final MountainReviewRepository mountainReviewRepository;
    private final CourseReviewRepository courseReviewRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MountainReviewService(MountainReviewRepository mountainReviewRepository,
                                 CourseReviewRepository courseReviewRepository, ModelMapper modelMapper) {
        this.mountainReviewRepository = mountainReviewRepository;
        this.courseReviewRepository = courseReviewRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void registMountain(MountainReviewDTO newReview) {   // insert - 산 후기
        mountainReviewRepository.save(modelMapper.map(newReview, MountainReview.class));
    }

    @Transactional
    public void modifyMountain(MountainReviewDTO modifyReview) {    // update - 산 후기
        MountainReview foundReview = mountainReviewRepository.findById(modifyReview.getId()).get();
        foundReview.setContent(modifyReview.getContent());
    }

    @Transactional
    public void deleteMountain(long reviewCode) {   // delete - 산 후기
        mountainReviewRepository.deleteById(reviewCode);
    }

    @Transactional
    public void registCourse(CourseReviewDTO newReview) {   // insert - 코스 후기
        courseReviewRepository.save(modelMapper.map(newReview, CourseReview.class));
    }

    @Transactional
    public void modifyCourse(CourseReviewDTO modifyReview) {    // update - 코스 후기
        CourseReview foundReview = courseReviewRepository.findById(modifyReview.getId()).get();
        foundReview.setReviewContent(modifyReview.getReviewContent());
    }

    @Transactional
    public void deleteCourse(long reviewCode) {     // delete - 코스 후기
        courseReviewRepository.deleteById(reviewCode);
    }
}
