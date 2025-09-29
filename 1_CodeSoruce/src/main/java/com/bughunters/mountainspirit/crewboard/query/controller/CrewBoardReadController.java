package com.bughunters.mountainspirit.crewboard.query.controller;

import com.bughunters.mountainspirit.board.query.dto.PageBoardDTO;
import com.bughunters.mountainspirit.common.Pagination;
import com.bughunters.mountainspirit.common.PagingButtonInfo;
import com.bughunters.mountainspirit.crewboard.query.dto.CrewBoardDTO;
import com.bughunters.mountainspirit.crewboard.query.dto.CrewBoardNameDTO;
import com.bughunters.mountainspirit.crewboard.query.service.CrewBoardReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CrewBoardReadController {
    private final CrewBoardReadService crewBoardReadService;

    @Autowired
    public CrewBoardReadController(CrewBoardReadService crewBoardReadService) {
        this.crewBoardReadService = crewBoardReadService;
    }

    @GetMapping("crewboards/list")
    public PageBoardDTO getBoards(@PageableDefault Pageable pageable) {
        Page<CrewBoardNameDTO> boardList = crewBoardReadService.getBoardName(pageable);

        PagingButtonInfo paging = Pagination.getPaginButtonInfo(boardList);
        return new PageBoardDTO(boardList.getContent(), paging);
    }

    @GetMapping("/crewboards/list/{id}/select")
    public List<CrewBoardDTO> findDetailBoardById(@PathVariable int id) {
        List<CrewBoardDTO> crewBoardDTOList = crewBoardReadService.getBoardInfo(id);

        return crewBoardDTOList;
    }

    @GetMapping("/crewboards/search/{keyword}")
    public List<CrewBoardDTO> findDetailBoardByKeyword(@PathVariable String keyword) {
        List<CrewBoardDTO> crewBoardDTOList = crewBoardReadService.getBoardInfoByKeyword(keyword);

        return crewBoardDTOList;
    }
}
