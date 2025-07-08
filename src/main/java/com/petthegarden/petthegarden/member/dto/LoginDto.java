package com.petthegarden.petthegarden.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotBlank(message = "아이디는 필수입력사항입니다.")
    private String userID;

    @NotBlank(message = "패스워드는 필수입력사항입니다.")
    private String userPW;
}
