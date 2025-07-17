package com.petthegarden.petthegarden.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminMemberDto {
    private Integer memberID;

    private String userID;

    private String userName;

    private LocalDateTime regDate;

    private Long petCount;

    private Long boardCount;

}
