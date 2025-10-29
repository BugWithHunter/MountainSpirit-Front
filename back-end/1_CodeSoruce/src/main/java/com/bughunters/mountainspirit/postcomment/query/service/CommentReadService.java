package com.bughunters.mountainspirit.postcomment.query.service;

import com.bughunters.mountainspirit.postcomment.query.dto.CommentDTO;

import java.util.List;

public interface CommentReadService {
    List<CommentDTO> readCommentsByPostId(int id);
}
