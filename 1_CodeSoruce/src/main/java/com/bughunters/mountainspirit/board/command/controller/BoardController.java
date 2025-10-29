package com.bughunters.mountainspirit.board.command.controller;

import com.bughunters.mountainspirit.board.command.dto.BoardDTO;
import com.bughunters.mountainspirit.board.command.dto.BoardModifyDTO;
import com.bughunters.mountainspirit.board.command.dto.RequestRegistPostDTO;
import com.bughunters.mountainspirit.board.command.dto.ResponseRegistPostDTO;
import com.bughunters.mountainspirit.board.command.service.BoardService;
import com.bughunters.mountainspirit.common.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    @PostMapping(value = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRegistPostDTO> insertPostInBoard(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam(required = false) List<MultipartFile> multiFiles,
            HttpServletRequest request) {

        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        long userId = userInfo.getId();


        if (multiFiles == null) {
            multiFiles = new ArrayList<>();
        }
        RequestRegistPostDTO newPost = new RequestRegistPostDTO();
        newPost.setTitle(title);
        newPost.setContent(content);
        BoardDTO boardDTO = modelMapper.map(newPost, BoardDTO.class);
        boardDTO.setCumId(userId);

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
    public void deleteBoardImage(@PathVariable long id) {
        boardService.deleteBoardImage(id);   // id는 이미지id임
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id) {
        boardService.deletePostById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/select/likes")
    public String pushLikes(@PathVariable int id,
                          HttpServletRequest request) {

        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        long userId = userInfo.getId();

        String result = boardService.createOrDeleteLikesByPostId(id, userId);
        return result;
    }

}
