package com.bughunters.mountainspirit.crewmember.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="CrewMemberRole")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewMemberRole {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String crewRoleName;
}
