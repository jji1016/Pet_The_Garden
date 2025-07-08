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

    private Integer memberId;

    private Integer petId;


//    public Diary toDiary(Member member, Pet pet) {
//        return Diary.builder()
//                .subject(this.subject)
//                .content(this.content)
//                .regDate(LocalDateTime.now())
//                .member(member)
//                .pet(pet)
//                .build();
//
//    }

    public Diary toDiary(Member member, Pet pet) {
        return Diary.builder()
                .subject(this.subject)
                .content(this.content)
                .regDate(LocalDateTime.now())
                .member(member)
                .pet(pet)
                .build();
    }

}

