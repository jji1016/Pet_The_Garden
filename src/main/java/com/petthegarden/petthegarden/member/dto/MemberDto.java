package com.petthegarden.petthegarden.member.dto;

import com.petthegarden.petthegarden.constant.Role;
import com.petthegarden.petthegarden.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    private Integer memberID;

    @NotBlank(message="아이디는 필수입력사항입니다.")
    private String userID;

    @NotBlank(message="패스워드는 필수입력사항입니다.")
    private String userPW;

    @NotBlank(message="이름은 필수입력사항입니다.")
    private String userName;

    @Email(message = "이메일형식에 맞게 쓰세요.")
    @NotBlank(message="이메일은 필수입력사항입니다.")
    private String email;

    @NotBlank(message="전화번호는 필수입력사항입니다.")
    private String tel;

    @NotNull(message="우편번호는 필수입력사항입니다.")
    private int zipcode;

    @NotBlank(message="주소는 필수입력사항입니다.")
    private String address01;

    private String address02;

    private boolean deleteStatus = false;

    private Role role;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;

    private MultipartFile imageProfile; //image file

    private String originalImage; //original name
    private String image; // renamed name


    public Member toMember() {
        return Member.builder()
                .userID(this.userID)
                .userPW(this.userPW)
                .userName(this.userName)
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
    public static MemberDto toMemberDto(Member member) {
        return MemberDto.builder()
                .userID(member.getUserID())
                .userName(member.getUserName())
                .email(member.getEmail())
                .build();
    }

}
