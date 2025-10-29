package com.bughunters.mountainspirit.crewmember.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="CrewApplyHistory")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewApplyHistory {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long crewId;
    @Column
    private String crewApplyHistoryDate;
    @Column
    private char crewApplyHistoryIsAccepted;
    @Column
    private String crewApplyHistoryAcceptDate;
    @Column
    private Long cumId;
}
