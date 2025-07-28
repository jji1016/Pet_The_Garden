package com.petthegarden.petthegarden.index.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Top3PetDto {
    private int id;
    private String image;
    private String petName;
    private String species;
    private String userName;
    private Integer followers;
}
