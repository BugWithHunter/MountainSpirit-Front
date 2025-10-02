package com.bughunters.mountainspirit.crewboard.query.service;


import com.bughunters.mountainspirit.crewboard.query.dto.CrewBoardDTO;
import com.bughunters.mountainspirit.crewboard.query.dto.CrewBoardNameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrewBoardReadService {
    Page<CrewBoardNameDTO> getBoardName(Pageable pageable);

    List<CrewBoardDTO> getBoardInfo(int id);

    List<CrewBoardDTO> getBoardInfoByKeyword(String keyword);
}
