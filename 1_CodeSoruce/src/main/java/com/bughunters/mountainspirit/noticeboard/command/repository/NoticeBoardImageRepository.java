package com.bughunters.mountainspirit.noticeboard.command.repository;

import com.bughunters.mountainspirit.noticeboard.command.entity.NoticeBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeBoardImageRepository extends JpaRepository<NoticeBoardImage, Long> {
}
