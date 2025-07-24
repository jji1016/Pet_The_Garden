package com.petthegarden.petthegarden.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminShowOffCommentDto {
    private Integer showOffID;

    private String content;

    private LocalDateTime regDate;

    private String boardSubject;

}
