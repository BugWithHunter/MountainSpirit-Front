package com.bughunters.mountainspirit.stamp.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MountainStampDTO {
    private long id;
    private LocalDateTime date;
    private long cumId;
    private String frtrlId;
}
