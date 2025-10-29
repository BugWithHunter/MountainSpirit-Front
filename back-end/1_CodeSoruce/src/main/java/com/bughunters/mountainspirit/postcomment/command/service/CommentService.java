package com.bughunters.mountainspirit.postcomment.command.service;

import com.bughunters.mountainspirit.postcomment.command.dto.RegistCommentDTO;

public interface CommentService {
    void registComment(RegistCommentDTO newComment, long userId);

    void modifyCommentById(String content, int id);

    void deleteCommentById(int id);
}
