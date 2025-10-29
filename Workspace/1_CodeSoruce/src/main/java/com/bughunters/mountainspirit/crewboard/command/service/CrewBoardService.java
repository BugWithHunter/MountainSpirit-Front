package com.bughunters.mountainspirit.crewboard.command.service;


import com.bughunters.mountainspirit.crewboard.command.dto.BoardDTO;
import com.bughunters.mountainspirit.crewboard.command.dto.BoardModifyDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CrewBoardService {
    void registPost(BoardDTO boardDTO, List<MultipartFile> multiFiles);

    void modifyPostById(BoardModifyDTO modifyPost, int id);

    void deletePostById(int id);

    String createOrDeleteLikesByPostId(int id, long userId);

    void addImageInPost(List<MultipartFile> multiFiles, int postid);

    void deleteBoardImage(long id);
}
