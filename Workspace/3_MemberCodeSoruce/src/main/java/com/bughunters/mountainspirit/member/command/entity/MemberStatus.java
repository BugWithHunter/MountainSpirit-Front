package com.bughunters.mountainspirit.member.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MemberStatus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status", nullable = false)
    private String status;
}
