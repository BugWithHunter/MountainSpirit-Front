package com.bughunters.mountainspirit.mountainbookmark.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MountainBookmarkDTO {
    private long id;
    private java.sql.Date time;
    private String frtrlId;
    private long cumId;
}
