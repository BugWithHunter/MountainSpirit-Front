package com.bughunters.mountainspirit.memberrank.command.repository;

import com.bughunters.mountainspirit.memberrank.command.entity.MountainRank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MountainRankRepository extends JpaRepository<MountainRank, Long> {
    List<MountainRank> findByFrtrlIdAndMtRankId(String frtrlId, long i);

    MountainRank findByCumId(Long cumId);

    List<MountainRank> findByFrtrlIdAndMtRankIdGreaterThanEqual(String frtrlId, Long i);

    MountainRank findByCumIdAndFrtrlId(long cumId, String frtrlId);
}
