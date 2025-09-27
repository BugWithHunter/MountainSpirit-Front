package com.bughunters.mountainspirit.stamp.command.repository;

import com.bughunters.mountainspirit.stamp.command.entity.MountainStamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MountainStampRepository extends JpaRepository<MountainStamp, Long> {
}
