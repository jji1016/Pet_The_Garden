package com.petthegarden.petthegarden.showoff.dto;

import com.petthegarden.petthegarden.entity.ShowOff;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CorrectDto {

    private Integer id;          // 수정 대상 PK
    private Integer petId;       // 표시 용
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 200)
    private String subject;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 4000)
    private String content;

    private String image;        // 이미지 URL
    private String youtubeLink;  // YouTube 링크

    /* 엔티티 → DTO 변환 */
    public static CorrectDto fromEntity(ShowOff s) {
        return CorrectDto.builder()
                .id(s.getId())
                .petId(s.getPet().getId())
                .subject(s.getSubject())
                .content(s.getContent())
                .image(s.getImage())
                .build();
    }
}
