package com.bughunters.mountainspirit.postcomment.command.controller;

import com.bughunters.mountainspirit.common.UserInfo;
import com.bughunters.mountainspirit.postcomment.command.dto.RegistCommentDTO;
import com.bughunters.mountainspirit.postcomment.command.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/boards/comment")      // 현재 상태
    public void writeCommentAtPost(@RequestBody RegistCommentDTO newComment,
                                   HttpServletRequest request) {

        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        long userId = userInfo.getId();

        commentService.registComment(newComment, userId);
    }

    /* 설명. 수정하고 싶은 상태 */
//    @PostMapping("/boards/{boardid}/comment")
//
//    @PostMapping("/crewboards/{crewboardid}/comment")
//
//    @PostMapping("/noticeboards/{noticeboardid}/comment")
//
//    @PostMapping("/perent-comment/{pid}/comment")




    @PutMapping("/comments/{id}/modify")
    public void modifyComment(@RequestParam String content, @PathVariable int id) {
        commentService.modifyCommentById(content, id);
    }

    @DeleteMapping("/comments/{id}/delete")
    public void deleteComment(@PathVariable int id) {
        commentService.deleteCommentById(id);
    }
}
