package com.bughunters.mountainspirit.stamp.command.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CourseStamp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "poiId", nullable = false)
    private String poiId;

    @Column(name = "cumId")
    private Long cumId;
}
