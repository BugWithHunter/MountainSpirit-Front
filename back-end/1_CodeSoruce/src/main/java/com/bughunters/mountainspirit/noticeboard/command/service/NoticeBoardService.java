package com.bughunters.mountainspirit.noticeboard.command.service;


import com.bughunters.mountainspirit.noticeboard.command.dto.BoardDTO;
import com.bughunters.mountainspirit.noticeboard.command.dto.BoardModifyDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NoticeBoardService {
    void registPost(BoardDTO boardDTO, List<MultipartFile> multiFiles);

    void modifyPostById(BoardModifyDTO modifyPost, int id);

    void deletePostById(int id);

    void addImageInPost(List<MultipartFile> multiFiles, int postid);

    void deleteBoardImage(long id);
}
