package com.bughunters.mountainspirit.stamp.command.repository;

import com.bughunters.mountainspirit.stamp.command.entity.CourseStamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseStampRepository extends JpaRepository<CourseStamp, Long> {

    List<CourseStamp> findByCumId(long cumId);
}
