package com.bughunters.mountainspirit.noticeboard.command.controller;

import com.bughunters.mountainspirit.noticeboard.command.dto.BoardDTO;
import com.bughunters.mountainspirit.noticeboard.command.dto.BoardModifyDTO;
import com.bughunters.mountainspirit.noticeboard.command.dto.RequestRegistPostDTO;
import com.bughunters.mountainspirit.noticeboard.command.dto.ResponseRegistPostDTO;
import com.bughunters.mountainspirit.noticeboard.command.service.NoticeBoardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/noticeboards")
public class NoticeBoardController {
    private final NoticeBoardService noticeBoardService;
    private final ModelMapper modelMapper;

    @Autowired
    public NoticeBoardController(NoticeBoardService noticeBoardService,
                                 ModelMapper modelMapper) {
        this.noticeBoardService = noticeBoardService;
        this.modelMapper = modelMapper;
    }

    /* 필기. 회원부분이랑 프로젝트 합쳐지면 @Authentication달기(userId 받아오기 위해서) */
    @PostMapping(value = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRegistPostDTO> insertPostInBoard(@RequestParam String title,
                                                                   @RequestParam String content,
                                                                   @RequestParam List<MultipartFile> multiFiles) {
        RequestRegistPostDTO newPost = new RequestRegistPostDTO();
        newPost.setTitle(title);
        newPost.setContent(content);
        BoardDTO boardDTO = modelMapper.map(newPost, BoardDTO.class);

        noticeBoardService.registPost(boardDTO, multiFiles);
        ResponseRegistPostDTO responsePost = modelMapper.map(boardDTO, ResponseRegistPostDTO.class);

        return ResponseEntity.ok().body(responsePost);
    }

    @PutMapping("/modify/{id}")
    public void modifyPost(@ModelAttribute BoardModifyDTO modifyPost,
                                       @PathVariable int id) {
        noticeBoardService.modifyPostById(modifyPost, id);

    }

    @PostMapping("/modify-boardimg/{postid}/add")
    public void addBoardImage(@RequestParam List<MultipartFile> multiFiles, @PathVariable int postid) {
        noticeBoardService.addImageInPost(multiFiles, postid);
    }

    @DeleteMapping("/modify-boardimg/{id}/delete")
    public void deleteBoardImage(@PathVariable long id) {
        noticeBoardService.deleteBoardImage(id);   // id는 이미지id임
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id) {
        noticeBoardService.deletePostById(id);

        return ResponseEntity.noContent().build();
    }

}
