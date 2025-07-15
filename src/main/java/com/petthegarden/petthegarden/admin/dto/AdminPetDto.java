package com.petthegarden.petthegarden.admin.dto;

import com.petthegarden.petthegarden.constant.PetGender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminPetDto {
    private Integer petID;

    private String profileImg;

    private String petName;

    private String species;

    private String follow;

    private PetGender petGender;

    private LocalDateTime regDate;
}
