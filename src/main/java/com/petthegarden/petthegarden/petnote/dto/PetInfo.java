package com.petthegarden.petthegarden.petnote.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetInfo {
//    private boolean isOwner;
    private int followers;
    private int followings;
    private int diaryCount;
    private int showOffCount;
}
