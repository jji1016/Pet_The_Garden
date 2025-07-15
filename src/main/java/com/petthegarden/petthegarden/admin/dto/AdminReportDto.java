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
    /* member */
    private String reporter;

    /* board */
    private Integer boardID;

    private String subject;

    private String writer;

    /* report */
    private Integer reportID;

    private LocalDateTime reportDate;

    private String reason;

    private ReportType type;



}
