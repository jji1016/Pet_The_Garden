package com.petthegarden.petthegarden.member;

import com.petthegarden.petthegarden.constant.Gender;
import com.petthegarden.petthegarden.constant.Role;
import com.petthegarden.petthegarden.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    @NotBlank(message="아이디는 필수입력사항입니다.")
    private String userID;

    @NotBlank(message="패스워드는 필수입력사항입니다.")
    private String userPW;

    @NotBlank(message="유저네임은 필수입력사항입니다.")
    private String userName;

    @NotBlank(message="생년월일은 필수입력사항입니다.")
    private String birthDate;

    @NotNull(message="성별은 필수입력사항입니다.")
    private Gender gender;

    @Email(message = "이메일형식에 맞게 쓰세요.")
    @NotBlank(message="이메일은 필수입력사항입니다.")
    private String email;

    @NotBlank(message="전화번호는 필수입력사항입니다.")
    private String tel;

    @NotBlank(message="우편번호는 필수입력사항입니다.")
    private int zipcode;

    @NotBlank(message="주소는 필수입력사항입니다.")
    private String address01;

    private String address02;

    private String image;

    private boolean deleteStatus = false;

    private Role role;


    public Member toMember() {
        return Member.builder()
                .userID(this.userID)
                .userPW(this.userPW)
                .userName(this.userName)
                .birthDate(this.birthDate)
                .gender(this.gender)
                .email(this.email)
                .tel(this.tel)
                .zipcode(this.zipcode)
                .address01(this.address01)
                .address02(this.address02)
                .image(this.image)
                .deleteStatus(this.deleteStatus)
                .role(this.role)
                .build();
    }

}
