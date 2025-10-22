package com.bughunters.mountainspirit.member.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseProfileImageDTO {
    Long cumId;
    String dirPath;
    String filePath;
    boolean successUpload;
    String exceptionMessage;
}
