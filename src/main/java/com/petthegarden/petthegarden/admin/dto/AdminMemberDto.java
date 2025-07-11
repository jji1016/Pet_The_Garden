package com.petthegarden.petthegarden.admin.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminMemberDto {
    private Integer memberID;

    private String userID;

    private String userPW;

    private String userName;

    private String image;

    private String email;

    private String tel;

    private LocalDateTime regDate;

    private int zipcode;

    private String address01;

    private String address02;

}
