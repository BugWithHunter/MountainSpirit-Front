package com.bughunters.mountainspirit.stamp.query.mapper;

import com.bughunters.mountainspirit.stamp.query.dto.MountainStampDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MountainStampGetMapper {
    List<MountainStampDTO> selectMountainStampById(long memberId);
}
