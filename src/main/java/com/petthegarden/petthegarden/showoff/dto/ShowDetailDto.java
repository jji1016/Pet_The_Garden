package com.petthegarden.petthegarden.showoff.dto;

import com.petthegarden.petthegarden.entity.ShowOff;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowDetailDto {
    private ShowOff showOff;
    private String youtubeUrl;
    private String youtubeId;
    private boolean isAuthor;
}
