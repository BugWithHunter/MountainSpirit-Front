package com.bughunters.mountainspirit.memberrank.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MountainRankStandard")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MountainRankStandard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rankName", nullable = false)
    private String rankName;
}
