package com.bughunters.mountainspirit.crew.command.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Crew")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Crew {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="crewIsRecruit")
    private char crewIsRecruit;
    @Column(name="crewCreateDate")
    private LocalDateTime crewCreateDate;
    @Column(name="crewMaxPeople")
    private Integer crewMaxPeople;
    @Column(name="crewIntro")
    private String crewIntro;
    @Column(name="crewIsDeleted")
    private char crewIsDeleted;
    @Column(name="crewBanDate")
    private int crewBanDate;
    @Column(name="crewRankGauge")
    private long crewRankGauge;
}
