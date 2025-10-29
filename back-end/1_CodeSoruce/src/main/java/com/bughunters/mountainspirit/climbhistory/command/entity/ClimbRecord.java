package com.bughunters.mountainspirit.climbhistory.command.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ClimbRecord")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClimbRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "startTime", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "endTime", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "cumId", nullable = false)
    private Long cumId;

    @Column(name = "frtrlId")
    private String frtrlId;

    @Column(name = "poiId", nullable = false)
    private String poiId;
}