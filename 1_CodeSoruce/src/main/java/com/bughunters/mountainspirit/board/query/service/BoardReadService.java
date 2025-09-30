package com.bughunters.mountainspirit.board.query.service;

import com.bughunters.mountainspirit.board.query.dto.BoardDTO;
import com.bughunters.mountainspirit.board.query.dto.BoardNameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardReadService {
    Page<BoardNameDTO> getBoardName(Pageable pageable);

    List<BoardDTO> getBoardInfo(int id);

    List<BoardDTO> getBoardInfoByKeyword(String keyword);
}
