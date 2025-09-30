package com.bughunters.mountainspirit.board.command.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private int id;

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
