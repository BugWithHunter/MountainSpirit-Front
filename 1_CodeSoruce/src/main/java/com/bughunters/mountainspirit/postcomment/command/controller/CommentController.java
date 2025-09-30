package com.bughunters.mountainspirit.postcomment.command.controller;

import com.bughunters.mountainspirit.board.command.dto.BoardDTO;
import com.bughunters.mountainspirit.board.command.dto.ResponseRegistPostDTO;
import com.bughunters.mountainspirit.postcomment.command.dto.RegistCommentDTO;
import com.bughunters.mountainspirit.postcomment.command.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/boards/comment")
    public void writeCommentAtPost(@RequestBody RegistCommentDTO newComment) {
        commentService.registComment(newComment);
    }

    @PutMapping("/comments/{id}/modify")
    public void modifyComment(@RequestParam String content, @PathVariable int id) {
        commentService.modifyCommentById(content, id);
    }

    @DeleteMapping("/comments/{id}/delete")
    public void deleteComment(@PathVariable int id) {
        commentService.deleteCommentById(id);
    }
}
