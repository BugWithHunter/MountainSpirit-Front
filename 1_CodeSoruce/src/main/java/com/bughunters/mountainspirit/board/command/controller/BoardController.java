package com.bughunters.mountainspirit.board.command.controller;

import com.bughunters.mountainspirit.board.command.dto.BoardDTO;
import com.bughunters.mountainspirit.board.command.dto.BoardModifyDTO;
import com.bughunters.mountainspirit.board.command.dto.RequestRegistPostDTO;
import com.bughunters.mountainspirit.board.command.dto.ResponseRegistPostDTO;
import com.bughunters.mountainspirit.board.command.entity.BoardImage;
import com.bughunters.mountainspirit.board.command.service.BoardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;
    private final ModelMapper modelMapper;

    @Autowired
    public BoardController(BoardService boardService,
                           ModelMapper modelMapper) {
        this.boardService = boardService;
        this.modelMapper = modelMapper;
    }

    /* 필기. 회원부분이랑 프로젝트 합쳐지면 @Authentication달기(userId 받아오기 위해서) */
    @PostMapping(value = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRegistPostDTO> insertPostInBoard(@RequestParam String title,
                                                                   @RequestParam String content,
                                                                   @RequestParam List<MultipartFile> multiFiles
                                                               /*    @AuthenticationPrincipal UserDetail userDetails */) {
//        Long userId = userDetails.getId();
        RequestRegistPostDTO newPost = new RequestRegistPostDTO();
        newPost.setTitle(title);
        newPost.setContent(content);
        BoardDTO boardDTO = modelMapper.map(newPost, BoardDTO.class);
//        boardDTO.setCumId(userId);

        boardService.registPost(boardDTO, multiFiles);
        ResponseRegistPostDTO responsePost = modelMapper.map(boardDTO, ResponseRegistPostDTO.class);

        return ResponseEntity.ok().body(responsePost);
    }

    @PutMapping("/modify/{id}")
    public void modifyPost(@ModelAttribute BoardModifyDTO modifyPost,
                                       @PathVariable int id) {
        boardService.modifyPostById(modifyPost, id);
    }

    @PostMapping("/modify-boardimg/{postid}/add")
    public void addBoardImage(@RequestParam List<MultipartFile> multiFiles, @PathVariable int postid) {
        boardService.addImageInPost(multiFiles, postid);
    }

    @DeleteMapping("/modify-boardimg/{id}/delete")
    public void deletePostById(@PathVariable long id) {
        boardService.deleteBoardImage(id);   // id는 이미지id임
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id) {
        boardService.deletePostById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/select/likes")
    public void pushLikes(@PathVariable int id) {
        boardService.createOrDeleteLikesByPostId(id);
    }

}
