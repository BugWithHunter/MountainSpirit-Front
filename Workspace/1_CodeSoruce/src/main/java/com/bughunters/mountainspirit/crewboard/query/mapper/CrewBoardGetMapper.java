package com.bughunters.mountainspirit.crewboard.query.mapper;

import com.bughunters.mountainspirit.crewboard.query.dto.CrewBoardDTO;
import com.bughunters.mountainspirit.crewboard.query.dto.CrewBoardNameDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CrewBoardGetMapper {
    List<CrewBoardNameDTO> selectBoardName(@Param("offset") int offset, @Param("limit") int limit);

    List<CrewBoardDTO> selectBoardInfoByPostId(int id);

    List<CrewBoardDTO> searchByTitle(String keyword);

    List<CrewBoardDTO> searchByContent(String keyword);

    List<CrewBoardDTO> searchByTitleOrContent(String keyword);

    long selectBoardNameCount();
}
