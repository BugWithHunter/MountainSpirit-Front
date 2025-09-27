package com.bughunters.mountainspirit.memberrank.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MountainRank")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MountainRank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mtRankId")
    private Long mtRankId;

    @Column(name = "cumId", nullable = false)
    private Long cumId;

    @Column(name = "frtrlId")
    private String frtrlId;
}
