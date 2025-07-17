package com.petthegarden.petthegarden.community.dto;

import com.petthegarden.petthegarden.constant.ReportType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDto {
    private String reason;
    private ReportType type; // 예: "BOARD", "COMMENT"
    private Integer repID; // 신고 대상 ID (게시글 ID 또는 댓글 ID)
}

