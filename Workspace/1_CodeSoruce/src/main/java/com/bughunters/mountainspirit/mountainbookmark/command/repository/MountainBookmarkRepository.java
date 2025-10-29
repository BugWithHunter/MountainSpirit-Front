package com.bughunters.mountainspirit.mountainbookmark.command.repository;

import com.bughunters.mountainspirit.mountainbookmark.command.entity.MountainBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MountainBookmarkRepository extends JpaRepository<MountainBookmark, Long> {
}
