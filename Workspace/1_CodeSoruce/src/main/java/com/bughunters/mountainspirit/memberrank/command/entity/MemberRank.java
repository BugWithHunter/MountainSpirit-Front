package com.bughunters.mountainspirit.memberrank.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MemberRank")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberRank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "standard", nullable = false)
    private Integer standard;
}