package com.bughunters.mountainspirit.crewmember.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="CrewMemberAuth")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewMemberAuth {
    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column
    private String crewRoleAuth;
    @Column
    private Long crewRoleId;
}
