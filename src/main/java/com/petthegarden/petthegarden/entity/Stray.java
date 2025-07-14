package com.petthegarden.petthegarden.entity;

import com.petthegarden.petthegarden.stray.StrayDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Stray {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "strayID")
    private Integer id;

    private String ABDM_IDNTFY_NO; // 유기 고유 번호

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

    public StrayDto toStrayDto() {
        return StrayDto.builder()
                .abdmIdNtfyNo(this.ABDM_IDNTFY_NO)
                .imageCours(this.IMAGE_COURS)
                .discvryPlcInfo(this.DISCVRY_PLC_INFO)
                .speciesNm(this.SPECIES_NM)
                .pblancBeginDe(this.PBLANC_BEGIN_DE)
                .pblancEndDe(this.PBLANC_END_DE)
                .colorNm(this.COLOR_NM)
                .stateNm(this.STATE_NM)
                .ageInfo(this.AGE_INFO)
                .bdwghInfo(this.BDWGH_INFO)
                .sexNm(this.SEX_NM)
                .neutYn(this.NEUT_YN)
                .sfetrInfo(this.SFETR_INFO)
                .shterNm(this.SHTER_NM)
                .shterTelno(this.SHTER_TELNO)
                .protectPlc(this.PROTECT_PLC)
                .build();
    }

}
