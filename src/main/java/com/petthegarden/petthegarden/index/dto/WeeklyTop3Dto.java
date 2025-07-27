package com.petthegarden.petthegarden.index.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeeklyTop3Dto {
    private Integer id;
    private String title;
    private String petName;
    private String image;
    private String content;
    private LocalDate regDate;
    private Integer likes;


    public String getYoutubeThumbnailUrl() {
        if (content == null) return null;

        String regex = "https?://(?:www\\.)?(?:youtube\\.com/watch\\?v=|youtu\\.be/)([\\w-]{11})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            String videoId = matcher.group(1);
            return "https://img.youtube.com/vi/" + videoId + "/hqdefault.jpg";
        }

        return null;
    }

}
