package com.petthegarden.petthegarden.stray;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StrayDto {
    @JsonProperty("ABDM_IDNTFY_NO")
    private String abdmIdNtfyNo; // 유기 고유 번호

    @JsonProperty("IMAGE_COURS")
    private String imageCours; // 이미지 경로

    @JsonProperty("DISCVRY_PLC_INFO")
    private String discvryPlcInfo; // 발견장소

    @JsonProperty("SPECIES_NM")
    private String speciesNm; // 품종

    @JsonProperty("PBLANC_BEGIN_DE")
    private String pblancBeginDe; // 공고 시작 날짜

    @JsonProperty("PBLANC_END_DE")
    private String pblancEndDe; // 공고 종료 날짜

    @JsonProperty("COLOR_NM")
    private String colorNm; // 색상

    @JsonProperty("STATE_NM")
    private String stateNm; // 상태

    @JsonProperty("AGE_INFO")
    private String ageInfo; // 나이

    @JsonProperty("BDWGH_INFO")
    private String bdwghInfo; // 몸무게

    @JsonProperty("SEX_NM")
    private String sexNm; // 성별

    @JsonProperty("NEUT_YN")
    private String neutYn; // 중성화 여부

    @JsonProperty("SFETR_INFO")
    private String sfetrInfo; // 특징

    @JsonProperty("SHTER_NM")
    private String shterNm; // 보호소명

    @JsonProperty("SHTER_TELNO")
    private String shterTelno; // 보호소 번호

    @JsonProperty("PROTECT_PLC")
    private String protectPlc; // 보호 장소

    public String getSexNmKor() {
        if (sexNm == null) return "알 수 없음";
        switch (sexNm) {
            case "M": return "수컷";
            case "F": return "암컷";
            case "Q": return "알 수 없음";
            default: return sexNm;
        }
    }

    public String getNeutYnKor() {
        if (neutYn == null) return "미상";
        switch (neutYn) {
            case "Y": return "예";
            case "N": return "아니오";
            case "U": return "미상";
            default: return neutYn;
        }
    }
}
