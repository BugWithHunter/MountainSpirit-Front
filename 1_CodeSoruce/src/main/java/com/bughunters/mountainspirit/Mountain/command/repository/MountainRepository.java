package com.bughunters.mountainspirit.Mountain.command.repository;

import com.bughunters.mountainspirit.Mountain.command.entity.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MountainRepository extends JpaRepository<Mountain, String> {
    @Query("select m from Mountain m ")
    List<Mountain> findAllCourses();
}
