package com.bughunters.mountainspirit.board.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Likes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Likes {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "cumId")
    private long cumId;

    @Column(name = "postId")
    private Long postId;

    @Column(name = "crewPostId")
    private Long crewPostId;
}
