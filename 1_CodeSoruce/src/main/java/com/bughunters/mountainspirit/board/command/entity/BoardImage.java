package com.bughunters.mountainspirit.board.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BoardImage")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardImage {

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
