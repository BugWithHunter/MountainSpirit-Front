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
        int pageNum = pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1;
        int pageSize = 7;

        pageable = PageRequest.of(pageNum, pageSize);
        int offset = (pageNum) * pageSize;

        List<BoardNameDTO> boardList = noticeBoardGetMapper.selectBoardName(offset, pageSize);
        long totalCount = noticeBoardGetMapper.selectBoardNameCount();

        Page<BoardNameDTO> boardPage = new PageImpl<>(boardList, pageable, totalCount);

        return boardPage;
    }

    @Override
    public List<BoardDTO> getBoardInfo(int id) {
        List<BoardDTO> boardDTOList = noticeBoardGetMapper.selectBoardInfoByPostId(id);

        return boardDTOList;
    }

    @Override
    public List<BoardDTO> getBoardInfoByKeyword(String keyword, String type) {
        List<BoardDTO> boardDTOList;

        if ("title".equals(type)) {
            boardDTOList = noticeBoardGetMapper.searchByTitle(keyword);
        } else if ("content".equals(type)) {
            boardDTOList = noticeBoardGetMapper.searchByContent(keyword);
        } else {
            boardDTOList = noticeBoardGetMapper.searchByTitleOrContent(keyword);
        }
        return boardDTOList;
    }
}
