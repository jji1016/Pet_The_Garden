package com.petthegarden.petthegarden.showoff.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/showoff")
@RequiredArgsConstructor
@Slf4j
public class ShowListController {

    @GetMapping("/showlist")
    public String showList(Model model) {
        log.info("ShowOff 목록 페이지 요청");

        try {
            return "showoff/showlist"; // templates/showoff/showlist.html
        } catch (Exception e) {
            log.error("ShowOff 목록 조회 중 오류 발생", e);
            model.addAttribute("errorMessage", "페이지를 불러오는 중 오류가 발생했습니다.");
            return "error/error";
        }
    }
}
