package com.bughunters.mountainspirit.mountain.command.dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseCourseDTO {
    private String poiId;
    private String placeNm;
    private String frtrlId;
    private Long rankId;
}
