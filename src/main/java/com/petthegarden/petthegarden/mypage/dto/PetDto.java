package com.petthegarden.petthegarden.mypage.dto;

import com.petthegarden.petthegarden.constant.PetGender;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetDto {
    private Integer id;
    private String species;
    private String petName;
    private String birthDate;
    private String profileImg;
    private String content;
    private String follow;
    private String character;
    private String petLike;
    private String petDisLike;
    private PetGender petGender;
    private Integer memberId;

    public Pet toEntity(Member member) {
        return Pet.builder()
                .species(species)
                .petName(petName)
                .birthDate(birthDate)
                .profileImg(profileImg)
                .content(content)
                .follow(follow)
                .character(character)
                .petLike(petLike)
                .petDisLike(petDisLike)
                .petGender(petGender)
                .member(member)
                .build();
    }
}