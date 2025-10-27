package com.bughunters.mountainspirit.board.command.service;

import com.bughunters.mountainspirit.board.command.dto.BoardDTO;
import com.bughunters.mountainspirit.board.command.dto.BoardModifyDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    void registPost(BoardDTO boardDTO, List<MultipartFile> multiFiles);

    void modifyPostById(BoardModifyDTO modifyPost, int id);

    void deletePostById(int id);

    void createOrDeleteLikesByPostId(int id, long userId);

    void deleteBoardImage(long id);

    void addImageInPost(List<MultipartFile> multiFiles, int postid);
}
