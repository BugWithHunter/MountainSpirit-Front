package com.bughunters.mountainspirit.mountainreview.query.service;

import com.bughunters.mountainspirit.mountainreview.query.dto.MountainReviewDTO;
import com.bughunters.mountainspirit.mountainreview.query.mapper.MountainReviewGetMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MountainReviewServiceImpl implements MountainReviewService {
    private MountainReviewGetMapper mountainReviewGetMapper;

    private final SqlSessionTemplate sqlSession;

    @Autowired
    public MountainReviewServiceImpl(MountainReviewGetMapper mountainReviewGetMapper, SqlSessionTemplate sqlSession) {
        this.mountainReviewGetMapper = mountainReviewGetMapper;
        this.sqlSession = sqlSession;
    }

    @Override
    public List<MountainReviewDTO> getMountainReview(String mountainName) {
        List<MountainReviewDTO> mountainReviewList = mountainReviewGetMapper.selectMountainReviewByName(mountainName);
        return mountainReviewList;
    }
}
