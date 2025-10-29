package com.bughunters.mountainspirit.postcomment.query.controller;

import com.bughunters.mountainspirit.postcomment.query.dto.CommentDTO;
import com.bughunters.mountainspirit.postcomment.query.service.CommentReadService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentReadController {
    private final CommentReadService commentReadService;
    private final ModelMapper modelMapper;

    public CommentReadController(CommentReadService commentReadService, ModelMapper modelMapper) {
        this.commentReadService = commentReadService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("{id}/comments/read")     // uri 수정해야 함/ id는 게시글id임
    public List<CommentDTO> readComments(@PathVariable int id) {
        List<CommentDTO> commentDTOList = commentReadService.readCommentsByPostId(id);

        return commentDTOList;
    }
}
