package com.bughunters.mountainspirit.board.query.service;

import com.bughunters.mountainspirit.board.query.dto.BoardDTO;
import com.bughunters.mountainspirit.board.query.dto.BoardNameDTO;
import com.bughunters.mountainspirit.board.query.mapper.BoardGetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BoardReadServiceImpl implements BoardReadService {

    private BoardGetMapper boardGetMapper;

    @Autowired
    public BoardReadServiceImpl(BoardGetMapper boardGetMapper) {
        this.boardGetMapper = boardGetMapper;
    }


    @Override
    public Page<BoardNameDTO> getBoardName(Pageable pageable) {
        int pageNum = pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1;
        int pageSize = 7;
        pageable = PageRequest.of(pageNum, pageSize);
        int offset = (pageNum) * pageSize;
        List<BoardNameDTO> boardList = boardGetMapper.selectBoardName(offset, pageSize);
        long totalCount = boardGetMapper.selectBoardNameCount();
        Page<BoardNameDTO> boardPage = new PageImpl<>(boardList, pageable, totalCount);

        return boardPage;
    }


    @Override
    public List<BoardDTO> getBoardInfo(int id) {
        List<BoardDTO> boardDTOList = boardGetMapper.selectBoardInfoByPostId(id);

        return boardDTOList;
    }

    @Override
    public List<BoardDTO> getBoardInfoByKeyword(String keyword, String type) {

        List<BoardDTO> boardDTOList;

        /* 설명. type은 "title", "content", "title_content" 중 하나를 선택 */
        if ("title".equals(type)) {
            boardDTOList = boardGetMapper.searchByTitle(keyword);
        } else if ("content".equals(type)) {
            boardDTOList = boardGetMapper.searchByContent(keyword);
        } else {
            boardDTOList = boardGetMapper.searchByTitleOrContent(keyword);
        }

        return boardDTOList;
    }
}
