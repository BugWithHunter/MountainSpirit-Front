package com.bughunters.mountainspirit.postcomment.command.service;

import com.bughunters.mountainspirit.postcomment.command.dto.RegistCommentDTO;
import com.bughunters.mountainspirit.postcomment.command.entity.Comment;
import com.bughunters.mountainspirit.postcomment.command.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final ModelMapper modelMapper;
    private CommentRepository commentRepository;

    public CommentServiceImpl(ModelMapper modelMapper, CommentRepository commentRepository) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public void registComment(RegistCommentDTO newComment) {
        Comment comment = modelMapper.map(newComment, Comment.class);
        comment.setCreateDate(LocalDateTime.now());
        comment.setCumId(185);  // 임의 설정
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void modifyCommentById(String content, int id) {
        Comment comment = commentRepository.findById(id).get();
        log.info("service계층에서의 수정된 Comment 내용: {}", comment);
        comment.setContent(content);
        comment.setModifyDate(LocalDateTime.now());
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteCommentById(int id) {
        Comment comment = commentRepository.findById(id).get();
        commentRepository.delete(comment);
    }
}
