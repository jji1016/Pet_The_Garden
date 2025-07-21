package com.petthegarden.petthegarden.petnote.dto;

import com.petthegarden.petthegarden.entity.Diary;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    private MultipartFile diaryImg;

    private String image;

    private LocalDateTime regDate;

    private LocalDateTime modifyDate;

    private Member member;

    private Pet pet;


    public static Diary toDiary(DiaryDto diaryDto) {
        return Diary.builder()
                .Id(diaryDto.getId())
                .subject(diaryDto.getSubject())
                .content(diaryDto.getContent())
                .image(diaryDto.getImage())
                .member(diaryDto.getMember())
                .pet(diaryDto.getPet())
                .build();
    }

    public static DiaryDto toDiaryDto(Diary diary) {
        return DiaryDto.builder()
                .Id(diary.getId())
                .subject(diary.getSubject())
                .content(diary.getContent())
                .image(diary.getImage())
                .regDate(diary.getRegDate())
                .modifyDate(diary.getModifyDate())
                .member(diary.getMember())
                .pet(diary.getPet())
                .build();
    }

    public static List<DiaryDto> toDiaryDtoList(List<Diary> diaryList) {
        return diaryList.stream()
                .map(DiaryDto::toDiaryDto)
                .collect(Collectors.toList());
    }
}

