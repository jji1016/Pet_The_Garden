package com.petthegarden.petthegarden.showoff.dto;

import com.petthegarden.petthegarden.constant.PetGender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShowPetDto {
    private Integer id;

    private LocalDateTime regDate;

    private LocalDateTime modifyDate;

    private String species;

    private String petName;

    private String birthDate;

    private String profileImg;

    private String content;

    private Long follow;

    private String character;

    private String petLike;

    private String petDisLike;

    private PetGender petGender;

    private String memberID;
}
