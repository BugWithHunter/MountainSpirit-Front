package com.bughunters.mountainspirit.noticeboard.query.controller;

import com.bughunters.mountainspirit.common.Pagination;
import com.bughunters.mountainspirit.common.PagingButtonInfo;
import com.bughunters.mountainspirit.noticeboard.query.dto.BoardDTO;
import com.bughunters.mountainspirit.noticeboard.query.dto.BoardNameDTO;
import com.bughunters.mountainspirit.noticeboard.query.dto.PageBoardDTO;
import com.bughunters.mountainspirit.noticeboard.query.service.NoticeBoardReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoticeBoardReadController {
    private final NoticeBoardReadService noticeBoardReadService;

    @Autowired
    public NoticeBoardReadController(NoticeBoardReadService noticeBoardReadService) {
        this.noticeBoardReadService = noticeBoardReadService;
    }

    @GetMapping("noticeboards/list")
    public PageBoardDTO getBoards(@PageableDefault Pageable pageable) {
        Page<BoardNameDTO> boardList = noticeBoardReadService.getBoardName(pageable);

        PagingButtonInfo paging = Pagination.getPaginButtonInfo(boardList);
        return new PageBoardDTO(boardList.getContent(), paging);
    }

    @GetMapping("/noticeboards/list/{id}/select")
    public List<BoardDTO> findDetailBoardById(@PathVariable int id) {
        List<BoardDTO> boardDTOList = noticeBoardReadService.getBoardInfo(id);

        return boardDTOList;
    }

    @GetMapping("/noticeboards/search")
    public List<BoardDTO> findDetailBoardByKeyword(@RequestParam String type,
                                                   @RequestParam String keyword) {
        List<BoardDTO> boardDTOList = noticeBoardReadService.getBoardInfoByKeyword(keyword, type);

        return boardDTOList;
    }
}
