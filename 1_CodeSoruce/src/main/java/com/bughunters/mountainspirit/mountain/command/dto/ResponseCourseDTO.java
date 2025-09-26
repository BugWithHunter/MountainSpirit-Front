package com.bughunters.mountainspirit.stamp.command.dto;
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
}
