package com.petthegarden.petthegarden.admin.dto;

import com.petthegarden.petthegarden.constant.ReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminReportDto {
    /* report */
    private Integer reportID;

    private LocalDateTime reportDate;

    private String reason;

    private ReportType type;

    private Integer repID; //Board, BoardComment, ShowOff, ShowOffComment의 PK

    /* member */
    private String reporter;

    /* Board, BoardComment, ShowOff, ShowOffComment */
    private String subject;

    private String writer;

}
