package com.bughunters.mountainspirit.crewboard.query.service;

import com.bughunters.mountainspirit.crewboard.query.dto.CrewBoardDTO;
import com.bughunters.mountainspirit.crewboard.query.dto.CrewBoardNameDTO;
import com.bughunters.mountainspirit.crewboard.query.mapper.CrewBoardGetMapper;
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
public class CrewBoardReadServiceImpl implements CrewBoardReadService {

    private CrewBoardGetMapper crewBoardGetMapper;

    @Autowired
    public CrewBoardReadServiceImpl(CrewBoardGetMapper crewBoardGetMapper) {
        this.crewBoardGetMapper = crewBoardGetMapper;
    }


    @Override
    public Page<CrewBoardNameDTO> getBoardName(Pageable pageable) {
        int pageNum = pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1;
        int pageSize = 7;
        pageable = PageRequest.of(pageNum, pageSize);
        int offset = (pageNum) * pageSize;
        List<CrewBoardNameDTO> boardList = crewBoardGetMapper.selectBoardName(offset, pageSize);
        long totalCount = crewBoardGetMapper.selectBoardNameCount();

        Page<CrewBoardNameDTO> boardPage = new PageImpl<>(boardList, pageable, totalCount);

        return boardPage;
    }

    @Override
    public List<CrewBoardDTO> getBoardInfo(int id) {
        List<CrewBoardDTO> crewBoardDTOList = crewBoardGetMapper.selectBoardInfoByPostId(id);

        return crewBoardDTOList;
    }

    @Override
    public List<CrewBoardDTO> getBoardInfoByKeyword(String keyword, String type) {
        List<CrewBoardDTO> boardDTOList;

        if ("title".equals(type)) {
            boardDTOList = crewBoardGetMapper.searchByTitle(keyword);
        } else if ("content".equals(type)) {
            boardDTOList = crewBoardGetMapper.searchByContent(keyword);
        } else {
            boardDTOList = crewBoardGetMapper.searchByTitleOrContent(keyword);
        }
        return boardDTOList;
    }
}
