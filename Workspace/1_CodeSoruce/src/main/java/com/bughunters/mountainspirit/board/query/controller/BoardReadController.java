package com.bughunters.mountainspirit.board.query.controller;

import com.bughunters.mountainspirit.board.query.dto.BoardDTO;
import com.bughunters.mountainspirit.board.query.dto.BoardNameDTO;
import com.bughunters.mountainspirit.board.query.dto.PageBoardDTO;
import com.bughunters.mountainspirit.board.query.service.BoardReadService;
import com.bughunters.mountainspirit.common.Pagination;
import com.bughunters.mountainspirit.common.PagingButtonInfo;
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
public class BoardReadController {
    private final BoardReadService boardReadService;

    @Autowired
    public BoardReadController(BoardReadService boardReadService) {
        this.boardReadService = boardReadService;
    }

    @GetMapping("boards/list")
    public PageBoardDTO getBoards(@PageableDefault Pageable pageable) {
        Page<BoardNameDTO> boardList = boardReadService.getBoardName(pageable);

        PagingButtonInfo paging = Pagination.getPaginButtonInfo(boardList);
        return new PageBoardDTO(boardList.getContent(), paging);
    }

    @GetMapping("/boards/list/{id}/select")
    public List<BoardDTO> findDetailBoardById(@PathVariable int id) {
        List<BoardDTO> boardDTOList = boardReadService.getBoardInfo(id);

        return boardDTOList;
    }

    @GetMapping("/boards/search")     // postman에서도 쿼리스트링으로 수정
    public List<BoardDTO> findDetailBoardByKeyword(@RequestParam String type,
                                                   @RequestParam String keyword) {
        List<BoardDTO> boardDTOList = boardReadService.getBoardInfoByKeyword(keyword, type);

        return boardDTOList;
    }
}
