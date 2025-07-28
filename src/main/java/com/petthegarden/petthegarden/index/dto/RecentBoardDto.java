package com.petthegarden.petthegarden.index.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecentBoardDto {
    private Integer Id;
    private String title;
    private LocalDate regDate;
    private String writer;
    private String userID;
}
