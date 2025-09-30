package com.bughunters.mountainspirit.crewboard.command.repository;

import com.bughunters.mountainspirit.crewboard.command.entity.CrewBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewBoardRepository extends JpaRepository<CrewBoard, Integer> {
}
