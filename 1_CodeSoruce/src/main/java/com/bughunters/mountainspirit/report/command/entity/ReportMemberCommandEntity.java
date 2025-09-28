package com.bughunters.mountainspirit.report.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportMemberCommandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private int banCnt; // 정지 횟수
}
