package com.bughunters.mountainspirit.memberrank.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ScoreStandard")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScoreStandard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "score", nullable = false)
    private Integer score;
}
