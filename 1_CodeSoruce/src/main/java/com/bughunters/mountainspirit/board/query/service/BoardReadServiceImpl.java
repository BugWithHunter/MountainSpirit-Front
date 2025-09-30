package com.bughunters.mountainspirit.board.query.service;

import com.bughunters.mountainspirit.board.query.dto.BoardDTO;
import com.bughunters.mountainspirit.board.query.dto.BoardNameDTO;
import com.bughunters.mountainspirit.board.query.mapper.BoardGetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

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
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize());
        List<BoardNameDTO> boardList = boardGetMapper.selectBoardName(pageable);
        Page<BoardNameDTO> boardPage = new PageImpl<>(boardList, pageable, boardList.size());

        return boardPage;
    }


    @Override
    public List<BoardDTO> getBoardInfo(int id) {
        List<BoardDTO> boardDTOList = boardGetMapper.selectBoardInfoByPostId(id);

        return boardDTOList;
    }

    @Override
    public List<BoardDTO> getBoardInfoByKeyword(String keyword) {
        List<BoardDTO> boardDTOList = boardGetMapper.selectBoardInfoByKeyword(keyword);

        return boardDTOList;
    }
}
