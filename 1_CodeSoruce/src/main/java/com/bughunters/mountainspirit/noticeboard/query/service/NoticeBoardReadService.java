package com.bughunters.mountainspirit.noticeboard.query.service;


import com.bughunters.mountainspirit.noticeboard.query.dto.BoardDTO;
import com.bughunters.mountainspirit.noticeboard.query.dto.BoardNameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeBoardReadService {
    Page<BoardNameDTO> getBoardName(Pageable pageable);

    List<BoardDTO> getBoardInfo(int id);

    List<BoardDTO> getBoardInfoByKeyword(String keyword);
}
