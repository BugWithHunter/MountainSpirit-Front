package com.bughunters.mountainspirit.crewboard.query.mapper;

import com.bughunters.mountainspirit.crewboard.query.dto.CrewBoardDTO;
import com.bughunters.mountainspirit.crewboard.query.dto.CrewBoardNameDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface CrewBoardGetMapper {
    List<CrewBoardNameDTO> selectBoardName(Pageable pageable);

    List<CrewBoardDTO> selectBoardInfoByPostId(int id);

    List<CrewBoardDTO> selectBoardInfoByKeyword(String keyword);
}
