package com.bughunters.mountainspirit.mountain.command.repository;

import com.bughunters.mountainspirit.mountain.command.entity.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MountainRepository extends JpaRepository<Mountain, String> {
    @Query("select m from Mountain m ")
    List<Mountain> findAllCourses();

    @Query("select m.frtrlNm from Mountain m where m.id = :frtrlId")
    String findNameById(@Param("frtrlId") String frtrlId);
}
