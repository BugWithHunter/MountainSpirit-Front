package com.bughunters.mountainspirit.stamp.command.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MountainStamp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MountainStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "cumId", nullable = false)
    private Long cumId;

    @Column(name = "frtrlId")
    private String frtrlId;
}
