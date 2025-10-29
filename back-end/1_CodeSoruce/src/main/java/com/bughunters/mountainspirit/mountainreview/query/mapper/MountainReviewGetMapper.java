package com.bughunters.mountainspirit.mountainreview.query.mapper;

import com.bughunters.mountainspirit.mountainreview.query.dto.MountainReviewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MountainReviewGetMapper {
    List<MountainReviewDTO> selectMountainReviewByName(String mountainName);
}
