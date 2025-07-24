package com.petthegarden.petthegarden.petnote.dto;

import com.petthegarden.petthegarden.constant.PetGender;
import com.petthegarden.petthegarden.entity.Pet;
import lombok.*;

import java.time.format.DateTimeFormatter;

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
    private String userID;
    private String userName;
    private String userImage;



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
                .userID(pet.getMember().getUserID())
                .userName(pet.getMember().getUserName())
                .userImage(pet.getMember().getImage())
                .build();
    }

    public PetDto(Integer petID, String species, String petName, String birthDate,
                  String profileImg, PetGender petGender, String userID) {
        this.petID = petID;
        this.species = species;
        this.petName = petName;
        this.birthDate = birthDate;
        this.profileImg = profileImg;
        this.petGender = petGender.toString();
        this.userID = userID;
    }

}


