package com.bughunters.mountainspirit.climbhistory.query.mapper;

import com.bughunters.mountainspirit.climbhistory.query.dto.MountainRankDTO;
import com.bughunters.mountainspirit.climbhistory.query.dto.SpiritProfileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MountainRankQueryRepository {
    List<MountainRankDTO> SelectMountainRank();

    List<SpiritProfileDTO> selectSpiritProfileMountain();
}
