package com.petthegarden.petthegarden.admin.dto;

import com.petthegarden.petthegarden.constant.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AdminMemberDetailDto {
    private Integer id;

    private String userID;

    private String userName;

    private String image;

    private String email;

    private String tel;

    private LocalDateTime regDate;

    private int zipcode;

    private String address01;

    private String address02;
}
