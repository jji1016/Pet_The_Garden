package com.petthegarden.petthegarden.petnote.dto;

import com.petthegarden.petthegarden.entity.Diary;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryDto {

    private Integer Id;

    @NotBlank(message="제목을 입력하세요.")
    private String subject;

    @NotBlank(message="내용을 입력하세요.")
    private String content;

    private LocalDateTime regDate;

    private LocalDateTime modifyDate;

    private Member member;

    private Pet pet;


    public static Diary toDiary(DiaryDto diaryDto) {
        return Diary.builder()
                .Id(diaryDto.getId())
                .subject(diaryDto.getSubject())
                .content(diaryDto.getContent())
                .member(diaryDto.getMember())
                .pet(diaryDto.getPet())
                .build();
    }
}

