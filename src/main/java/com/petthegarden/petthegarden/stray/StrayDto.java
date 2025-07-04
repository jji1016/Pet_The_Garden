package com.petthegarden.petthegarden.stray;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrayDto {
    private String IMAGE_COURS; //이미지 경로

    private String DISCVRY_PLC_INFO; //발견장소

    private String SPECIES_NM; //품종

    private String PBLANC_BEGIN_DE; //공고 시작 날짜

    private String PBLANC_END_DE; //공고 종료 날짜

    private String COLOR_NM; //색상

    private String STATE_NM; //상태

    private String AGE_INFO; //나이

    private String BDWGH_INFO; //몸무게

    private String SEX_NM; //성별

    private String NEUT_YN; //중성화 여부

    private String SFETR_INFO; //특징

    private String SHTER_NM; //보호소명

    private String SHTER_TELNO; //보호소 번호

    private String PROTECT_PLC; //보호 장소
}
