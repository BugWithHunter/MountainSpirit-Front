package com.bughunters.mountainspirit.crewboard.query.service;

import com.bughunters.mountainspirit.board.query.dto.BoardNameDTO;
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
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize());
        List<CrewBoardNameDTO> boardList = crewBoardGetMapper.selectBoardName(pageable);
        Page<CrewBoardNameDTO> boardPage = new PageImpl<>(boardList, pageable, boardList.size());

        return boardPage;
    }

    @Override
    public List<CrewBoardDTO> getBoardInfo(int id) {
        List<CrewBoardDTO> crewBoardDTOList = crewBoardGetMapper.selectBoardInfoByPostId(id);

        return crewBoardDTOList;
    }

    @Override
    public List<CrewBoardDTO> getBoardInfoByKeyword(String keyword) {
        List<CrewBoardDTO> crewBoardDTOList = crewBoardGetMapper.selectBoardInfoByKeyword(keyword);

        return crewBoardDTOList;
    }
}
