package com.bughunters.mountainspirit.postcomment.command.repository;

import com.bughunters.mountainspirit.postcomment.command.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
