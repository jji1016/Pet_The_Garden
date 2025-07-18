package com.petthegarden.petthegarden.showoff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BestShowOffDto {

    private Integer id;
    private String subject;
    private String content;
    private String image;
    private Integer showOffLike;
    private String userName;
    private String petName;
    private String species;
    private String profileImg;

    /**
     * 좋아요 수를 포맷팅된 문자열로 반환합니다.
     */
    public String getFormattedLikes() {
        if (showOffLike == null) {
            return "0";
        }

        if (showOffLike >= 1000) {
            return String.format("%.1fk", showOffLike / 1000.0);
        }

        return showOffLike.toString();
    }

    /**
     * 제목을 지정된 길이로 자릅니다.
     */
    public String getTruncatedSubject(int maxLength) {
        if (subject == null) {
            return "";
        }

        if (subject.length() <= maxLength) {
            return subject;
        }

        return subject.substring(0, maxLength) + "...";
    }

    /**
     * 이미지 URL이 있는지 확인합니다.
     */
    public boolean hasImage() {
        return image != null && !image.trim().isEmpty();
    }

    /**
     * 펫 프로필 이미지 URL이 있는지 확인합니다.
     */
    public boolean hasPetImage() {
        return profileImg != null && !profileImg.trim().isEmpty();
    }

    /**
     * 기본 이미지 URL을 반환합니다.
     */
    public String getDefaultImage() {
        return "/images/default-pet.png";
    }
}
