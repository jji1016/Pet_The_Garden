package com.petthegarden.petthegarden.petnote.dto;

import com.petthegarden.petthegarden.entity.Pet;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetDto {
    private Integer petID;
    private String species;
    private String petName;
    private String birthDate;
    private String profileImg;
    private String petGender;
    private String follow;
    private String content;
    private String character;
    private String petLike;
    private String petDislike;

    public static PetDto toPetDto(Pet pet) {
        return PetDto.builder()
                .petID(pet.getId())
                .species(pet.getSpecies())
                .petName(pet.getPetName())
                .birthDate(pet.getBirthDate())
                .profileImg(pet.getProfileImg())
                .petGender(pet.getPetGender().toString())
                .follow(pet.getFollow())
                .content(pet.getContent())
                .character(pet.getCharacter())
                .petLike(pet.getPetLike())
                .petDislike(pet.getPetDisLike())
                .build();
    }
}


