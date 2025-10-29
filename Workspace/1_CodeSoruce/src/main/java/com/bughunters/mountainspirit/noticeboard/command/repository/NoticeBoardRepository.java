package com.bughunters.mountainspirit.noticeboard.command.repository;

import com.bughunters.mountainspirit.noticeboard.command.entity.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Integer> {
}
