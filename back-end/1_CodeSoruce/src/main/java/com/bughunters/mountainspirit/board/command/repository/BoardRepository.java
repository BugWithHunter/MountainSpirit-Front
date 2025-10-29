package com.bughunters.mountainspirit.board.command.repository;

import com.bughunters.mountainspirit.board.command.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
