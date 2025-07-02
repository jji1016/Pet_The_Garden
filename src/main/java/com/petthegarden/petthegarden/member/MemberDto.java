package com.petthegarden.petthegarden.member;

import com.petthegarden.petthegarden.constant.Gender;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private String userID;
    private String userPW;
    private String userName;
    private String birthDate;
    private String userEmail;
    private String tel;
    private int zipcode;
    private String address01;
    private String address02;
    private Gender gender;

}
