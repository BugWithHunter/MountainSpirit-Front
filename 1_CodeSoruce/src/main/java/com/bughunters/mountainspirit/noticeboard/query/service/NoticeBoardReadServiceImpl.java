package com.bughunters.mountainspirit.noticeboard.query.service;

import com.bughunters.mountainspirit.noticeboard.query.dto.BoardDTO;
import com.bughunters.mountainspirit.noticeboard.query.dto.BoardNameDTO;
import com.bughunters.mountainspirit.noticeboard.query.mapper.NoticeBoardGetMapper;
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
public class NoticeBoardReadServiceImpl implements NoticeBoardReadService {

    private NoticeBoardGetMapper noticeBoardGetMapper;

    @Autowired
    public NoticeBoardReadServiceImpl(NoticeBoardGetMapper noticeBoardGetMapper) {
        this.noticeBoardGetMapper = noticeBoardGetMapper;
    }


    @Override
    public Page<BoardNameDTO> getBoardName(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize());
        List<BoardNameDTO> boardList = noticeBoardGetMapper.selectBoardName(pageable);
        Page<BoardNameDTO> boardPage = new PageImpl<>(boardList, pageable, boardList.size());

        return boardPage;
    }

    @Override
    public List<BoardDTO> getBoardInfo(int id) {
        List<BoardDTO> boardDTOList = noticeBoardGetMapper.selectBoardInfoByPostId(id);

        return boardDTOList;
    }

    @Override
    public List<BoardDTO> getBoardInfoByKeyword(String keyword) {
        List<BoardDTO> boardDTOList = noticeBoardGetMapper.selectBoardInfoByKeyword(keyword);

        return boardDTOList;
    }
}
