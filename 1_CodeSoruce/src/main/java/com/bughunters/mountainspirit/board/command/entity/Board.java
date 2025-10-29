package com.bughunters.mountainspirit.board.command.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Board")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Board {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "cumId")
    private long cumId;
}
