package com.bughunters.mountainspirit.mountainbookmark.query.mapper;

import com.bughunters.mountainspirit.mountainbookmark.query.dto.MountainBookmarkDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MountainBookmarkGetMapper {
    List<MountainBookmarkDTO> selectMountainBookmarkById(Long memberId);
}
