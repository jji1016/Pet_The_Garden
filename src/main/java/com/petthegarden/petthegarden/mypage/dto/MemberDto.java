package com.petthegarden.petthegarden.mypage.dto;

import com.petthegarden.petthegarden.constant.Role;
import com.petthegarden.petthegarden.entity.Member;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;


import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private Integer id;

    @NotBlank(message="아이디는 필수입력사항입니다.")
    @Pattern(regexp="^[a-z0-9]{6,16}$", message="아이디는 6~16자의 소문자 영문/숫자만 가능합니다.")
    private String userID;

    @NotBlank(message="패스워드는 필수입력사항입니다.")
    @Pattern(
            regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{6,16}$",
            message="비밀번호는 영문, 숫자, 특수문자 포함 6~16자여야 합니다.")
    private String userPW;

    @NotBlank(message="이름은 필수입력사항입니다.")
    private String userName;

    @Email
    @NotBlank(message="이메일은 필수입력사항입니다.")
    private String userEmail;


    @NotBlank
    @Pattern(regexp="^01\\d-\\d{3,4}-\\d{4}$", message="전화번호 형식이 올바르지 않습니다.")
    private String tel;

    @NotBlank(message="주소는 필수입력사항입니다.")
    private String address01;

    private String address02;

    @NotBlank(message="우편번호는 필수입력사항입니다.")
    private String zipcode;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime regDate;


    public Member toMember() {
        return Member.builder()
                .userID(this.userID)
                .userPW(this.userPW)
                .userName(this.userName)
                .email(this.userEmail)
                .tel(this.tel)
                .address01(this.address01)
                .address02(this.address02)
                .zipcode(Integer.parseInt(this.zipcode))
                .role(this.role)
                .build();
    }
}
