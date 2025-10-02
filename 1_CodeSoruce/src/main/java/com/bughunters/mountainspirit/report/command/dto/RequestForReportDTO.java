package com.bughunters.mountainspirit.report.command.dto;
import com.bughunters.mountainspirit.report.command.entity.ReportCategoryCommandEntity;
import com.bughunters.mountainspirit.report.command.entity.ReportCommandEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestForReportDTO {
    private long instanceCntNum;
    private ReportCategoryCommandEntity rcce;
    private ReportMemberDTO member;
    private ReportCommandEntity report;
    private String result;
    private ReportMemberUpdateDTO reportMemberUpdateDTO;
}
