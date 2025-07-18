package com.petthegarden.petthegarden.showoff.controller;

import com.petthegarden.petthegarden.showoff.dto.BestShowOffDto;
import com.petthegarden.petthegarden.showoff.service.BestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BestController {

    private final BestService bestService;

    @GetMapping("/showoff/best")
    public String showBestPage(
            @RequestParam(value = "month", required = false) Integer month,
            Model model) {

        // 현재 월을 기본값으로 설정
        if (month == null) {
            month = LocalDate.now().getMonthValue();
        }

        // 월별 베스트 게시글 3개 조회
        List<BestShowOffDto> bestPosts = bestService.getBestShowOffsByMonth(month);

        // 모델에 데이터 추가
        model.addAttribute("bestPosts", bestPosts);
        model.addAttribute("currentMonth", month);

        return "showoff/best";
    }
}
