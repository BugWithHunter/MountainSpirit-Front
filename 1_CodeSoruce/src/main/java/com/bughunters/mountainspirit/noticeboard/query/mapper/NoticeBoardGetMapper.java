package com.bughunters.mountainspirit.noticeboard.query.mapper;

import com.bughunters.mountainspirit.noticeboard.query.dto.BoardDTO;
import com.bughunters.mountainspirit.noticeboard.query.dto.BoardNameDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface NoticeBoardGetMapper {
    List<BoardNameDTO> selectBoardName(Pageable pageable);

    List<BoardDTO> selectBoardInfoByPostId(int id);

    List<BoardDTO> selectBoardInfoByKeyword(String keyword);
}
