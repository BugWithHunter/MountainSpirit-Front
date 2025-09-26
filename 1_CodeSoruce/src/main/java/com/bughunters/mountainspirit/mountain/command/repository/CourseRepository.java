package com.bughunters.mountainspirit.mountain.command.repository;


import com.bughunters.mountainspirit.mountain.command.entity.Course;
import com.bughunters.mountainspirit.mountain.command.entity.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,String> {
    @Query("select m from Course m ")
    List<Course> findAllCourses();
}
