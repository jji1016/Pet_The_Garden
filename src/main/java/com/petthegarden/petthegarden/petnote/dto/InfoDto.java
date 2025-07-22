package com.petthegarden.petthegarden.petnote.dto;

import com.petthegarden.petthegarden.entity.Member;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoDto {
    private boolean pageOwner;
    private Member member;
    private int followCount;
    private boolean followStatus;
}
