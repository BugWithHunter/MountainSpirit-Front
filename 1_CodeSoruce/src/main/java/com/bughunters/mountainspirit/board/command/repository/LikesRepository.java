package com.bughunters.mountainspirit.board.command.repository;

import com.bughunters.mountainspirit.board.command.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByPostId(long postId);
}
