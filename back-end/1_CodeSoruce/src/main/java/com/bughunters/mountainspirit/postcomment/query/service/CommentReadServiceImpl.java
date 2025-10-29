package com.bughunters.mountainspirit.postcomment.query.service;

import com.bughunters.mountainspirit.postcomment.query.dto.CommentDTO;
import com.bughunters.mountainspirit.postcomment.query.mapper.CommentGetMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentReadServiceImpl implements CommentReadService {
    private CommentGetMapper commentGetMapper;

    public CommentReadServiceImpl(CommentGetMapper commentGetMapper) {
        this.commentGetMapper = commentGetMapper;
    }

    @Override
    public List<CommentDTO> readCommentsByPostId(int id) {
        List<CommentDTO> commentDTOList = commentGetMapper.selectCommentByPostId(id);

        return commentDTOList;
    }
}
