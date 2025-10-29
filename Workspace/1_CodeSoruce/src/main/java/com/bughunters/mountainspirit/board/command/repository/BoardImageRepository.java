package com.bughunters.mountainspirit.board.command.repository;

import com.bughunters.mountainspirit.board.command.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
}
