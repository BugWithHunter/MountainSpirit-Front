package com.bughunters.mountainspirit.crewboard.command.repository;

import com.bughunters.mountainspirit.board.command.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewLikesRepository extends JpaRepository<Likes, Long> {
    Likes findByPostId(long postId);
}
