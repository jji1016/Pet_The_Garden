package com.petthegarden.petthegarden.admin.dto;

import com.petthegarden.petthegarden.constant.PetGender;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AdminPetDto {
    private Integer petID;

    private String profileImg;

    private String petName;

    private String species;

    private String follow;

    private PetGender petGender;

    private LocalDateTime regDate;
}
