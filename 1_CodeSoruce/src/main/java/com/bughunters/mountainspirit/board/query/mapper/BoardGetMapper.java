package com.bughunters.mountainspirit.board.query.mapper;

import com.bughunters.mountainspirit.board.query.dto.BoardDTO;
import com.bughunters.mountainspirit.board.query.dto.BoardNameDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface BoardGetMapper {
    List<BoardNameDTO> selectBoardName(Pageable pageable);

    List<BoardDTO> selectBoardInfoByPostId(int id);

    List<BoardDTO> selectBoardInfoByKeyword(String keyword);
}
