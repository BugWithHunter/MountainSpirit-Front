package com.bughunters.mountainspirit.crewboard.command.controller;

import com.bughunters.mountainspirit.common.UserInfo;
import com.bughunters.mountainspirit.crewboard.command.dto.BoardDTO;
import com.bughunters.mountainspirit.crewboard.command.dto.BoardModifyDTO;
import com.bughunters.mountainspirit.crewboard.command.dto.RequestRegistPostDTO;
import com.bughunters.mountainspirit.crewboard.command.dto.ResponseRegistPostDTO;
import com.bughunters.mountainspirit.crewboard.command.service.CrewBoardService;
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
@RequestMapping("/crewboards")
public class CrewBoardController {
    private final CrewBoardService crewBoardService;
    private final ModelMapper modelMapper;

    @Autowired
    public CrewBoardController(CrewBoardService crewBoardService,
                               ModelMapper modelMapper) {
        this.crewBoardService = crewBoardService;
        this.modelMapper = modelMapper;
    }

    /* 필기. 회원부분이랑 프로젝트 합쳐지면 @Authentication달기(userId 받아오기 위해서) */
    @PostMapping(value = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRegistPostDTO> insertPostInBoard(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam(required = false) List<MultipartFile> multiFiles,
            HttpServletRequest request) {

        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        long userId = userInfo.getId();

        // multiFiles == null 인 경우, 빈 리스트로 대체
        if (multiFiles == null) {
            multiFiles = new ArrayList<>();
        }
        RequestRegistPostDTO newPost = new RequestRegistPostDTO();
        newPost.setTitle(title);
        newPost.setContent(content);
        BoardDTO boardDTO = modelMapper.map(newPost, BoardDTO.class);
        boardDTO.setCumId(userId);

        crewBoardService.registPost(boardDTO, multiFiles);
        ResponseRegistPostDTO responsePost = modelMapper.map(boardDTO, ResponseRegistPostDTO.class);

        return ResponseEntity.ok().body(responsePost);
    }

    @PutMapping("/modify/{id}")
    public void modifyPost(@ModelAttribute BoardModifyDTO modifyPost,
                                       @PathVariable int id) {
        crewBoardService.modifyPostById(modifyPost, id);
    }

    @PostMapping("/modify-boardimg/{postid}/add")
    public void addBoardImage(@RequestParam List<MultipartFile> multiFiles, @PathVariable int postid) {
        crewBoardService.addImageInPost(multiFiles, postid);
    }

    @DeleteMapping("/modify-boardimg/{id}/delete")
    public void deleteBoardImage(@PathVariable long id) {
        crewBoardService.deleteBoardImage(id);   // id는 이미지id임
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id) {
        crewBoardService.deletePostById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/select/likes")
    public void pushLikes(@PathVariable int id,
                          HttpServletRequest request) {

        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        long userId = userInfo.getId();

        crewBoardService.createOrDeleteLikesByPostId(id, userId);
    }

}
