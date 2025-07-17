package com.petthegarden.petthegarden.mypage.dto;

import com.petthegarden.petthegarden.constant.PetGender;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;

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
                .regDate(regDate)
                .modifyDate(modifyDate)
                .build();
    }

    public static PetDto fromEntity(Pet pet) {
        return PetDto.builder()
                .id(pet.getId())
                .species(pet.getSpecies())
                .petName(pet.getPetName())
                .birthDate(pet.getBirthDate())
                .profileImg(pet.getProfileImg())
                .content(pet.getContent())
                .follow(pet.getFollow())
                .character(pet.getCharacter())
                .petLike(pet.getPetLike())
                .petDisLike(pet.getPetDisLike())
                .petGender(pet.getPetGender())
                .memberId(pet.getMember() != null ? pet.getMember().getId() : null)
                .regDate(pet.getRegDate())
                .modifyDate(pet.getModifyDate())
                .build();
    }
}