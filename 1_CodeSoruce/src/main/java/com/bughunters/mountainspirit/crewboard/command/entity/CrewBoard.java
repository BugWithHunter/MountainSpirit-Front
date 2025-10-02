package com.bughunters.mountainspirit.crewboard.command.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CrewBoard")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewBoard {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "createDate")
    private LocalDateTime createDate;

    @Column(name = "modifyDate")
    private LocalDateTime modifyDate;

    @Column(name = "isDeleted")
    private String isDeleted;

    @Column(name = "isNotice")
    private String isNotice;

    @Column(name = "crewId")
    private long crewId;

    @Column(name = "cumId")
    private long cumId;
}
