package com.bughunters.mountainspirit.crewboard.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CrewBoardImage")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewBoardImage {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "originName")
    private String originName;

    @Column(name = "renaming")
    private String renaming;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "path")
    private String path;

    @Column(name = "postId")
    private long postId;
}
