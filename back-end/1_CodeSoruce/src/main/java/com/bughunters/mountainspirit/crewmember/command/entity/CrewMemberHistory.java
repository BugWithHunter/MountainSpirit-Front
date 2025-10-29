package com.bughunters.mountainspirit.crewmember.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="CrewMemberHistory")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewMemberHistory {
    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long crewRoleId;
    @Column
    private Long crewId;
    @Column
    private String crewMemberHistoryJoinDate;
    @Column
    private String crewMemberHistoryStateUpdateDate;
    @Column
    private String crewMemberHistoryState;
    @Column
    private String crewMemberHistoryUpdateReason;
    @Column
    private Long cumId;
}
