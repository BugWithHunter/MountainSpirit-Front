package com.bughunters.mountainspirit.mountain.query.mapper;

import com.bughunters.mountainspirit.mountain.query.dto.CourseDTO;
import com.bughunters.mountainspirit.mountain.query.dto.MountainDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MountainGetMapper {
    List<MountainDTO> selectAllMountain();

    List<MountainDTO> selectMountainByName(String mountainName);

}
