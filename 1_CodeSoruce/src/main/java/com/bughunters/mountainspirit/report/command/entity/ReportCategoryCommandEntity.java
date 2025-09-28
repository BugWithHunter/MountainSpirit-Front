package com.bughunters.mountainspirit.report.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ReportCategory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportCategoryCommandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reportName;

    private int countStandard;
}
