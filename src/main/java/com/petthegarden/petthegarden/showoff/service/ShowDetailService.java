package com.petthegarden.petthegarden.showoff.service;

import com.petthegarden.petthegarden.entity.ShowOff;
import com.petthegarden.petthegarden.showoff.dto.ShowDetailDto;
import com.petthegarden.petthegarden.showoff.repository.ShowDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ShowDetailService {

    private final ShowDetailRepository repository;

    public ShowDetailDto getDetail(Integer id) {

        ShowOff showOff = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("없는 게시글"));

        // 유튜브 ID 추출 ([YouTube] 제거 이후 링크에서)
        String content = showOff.getContent();
        String youtubeUrl = null;
        String youtubeId = null;

        Pattern p = Pattern.compile("(https?://[\\w./?=&%-]+)");
        Matcher m = p.matcher(content);
        if (m.find()) {
            youtubeUrl = m.group(1);
            youtubeId = extractYoutubeId(youtubeUrl);
        }

        // 로그인 사용자가 작성자인지 여부
        boolean isAuthor = false;
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String loginId = auth.getName();
            isAuthor = showOff.getMember() != null &&
                    loginId.equals(showOff.getMember().getUserID());
        }

        return ShowDetailDto.builder()
                .showOff(showOff)
                .youtubeUrl(youtubeUrl)
                .youtubeId(youtubeId)
                .isAuthor(isAuthor)
                .build();
    }

    private String extractYoutubeId(String url) {
        // watch?v=ID, youtu.be/ID 형식 모두 처리
        Pattern p = Pattern.compile("(?:v=|be/)([\\w-]{11})");
        Matcher m = p.matcher(url);
        return m.find() ? m.group(1) : null;
    }
}
