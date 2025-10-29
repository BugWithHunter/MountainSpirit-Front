package com.bughunters.mountainspirit.noticeboard.query.mapper;

import com.bughunters.mountainspirit.noticeboard.query.dto.BoardDTO;
import com.bughunters.mountainspirit.noticeboard.query.dto.BoardNameDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeBoardGetMapper {
    List<BoardNameDTO> selectBoardName(@Param("offset") int offset, @Param("limit") int limit);

    List<BoardDTO> selectBoardInfoByPostId(int id);

    List<BoardDTO> searchByTitle(String keyword);

    List<BoardDTO> searchByContent(String keyword);

    List<BoardDTO> searchByTitleOrContent(String keyword);

    long selectBoardNameCount();
}
