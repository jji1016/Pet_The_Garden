package com.petthegarden.petthegarden.admin.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminShowOffDto {
    private Integer showOffLike;
    private String subject;
    private String writer;
    private LocalDateTime regDate;

}
