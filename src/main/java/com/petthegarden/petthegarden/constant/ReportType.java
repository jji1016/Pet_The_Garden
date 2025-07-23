package com.petthegarden.petthegarden.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ReportType {
    FREE_POST("자유게시판 게시글"),
    FREE_COMMENT("자유게시판 댓글");
    private final String label;

}
