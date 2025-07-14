package com.petthegarden.petthegarden.community.dto;

import com.petthegarden.petthegarden.entity.Board;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {
    private String subject;
    private String content;

    // entity로 변환 메서드 (member 등은 service에서 별도 세팅)
    public Board toEntity() {
        Board board = new Board();
        board.setSubject(this.subject);
        board.setContent(this.content);
        return board;
    }
}