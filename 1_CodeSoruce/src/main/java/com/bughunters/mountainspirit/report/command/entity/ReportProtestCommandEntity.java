package com.bughunters.mountainspirit.report.command.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ReportProtest")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportProtestCommandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "appealDate")
    private LocalDateTime appealDate;

    @Column(name = "adminId")
    private String adminId;

    @Column(name = "appealReason")
    private String appealReason;

    @Column(name = "changeStatusReason")
    private String changeStatusReason;

    @Column(name = "isAccepted")
    private String isAccepted;

    @Column(name = "reportedId")
    private Long reportedId;

    @Column(name = "reportId")
    private Long reportId;
}
