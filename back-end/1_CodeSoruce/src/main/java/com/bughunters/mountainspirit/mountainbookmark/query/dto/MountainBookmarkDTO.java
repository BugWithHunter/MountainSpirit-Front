package com.bughunters.mountainspirit.mountainbookmark.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MountainBookmarkDTO {
    private long id;
    private LocalDateTime time;
    private String frtrlId;
    private long cumId;
}
