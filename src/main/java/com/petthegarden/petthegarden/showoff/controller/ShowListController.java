package com.petthegarden.petthegarden.showoff.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShowListController {
    @GetMapping("/showoff/showlist")
    public String showShowoffPage() {
        // showlist.html을 템플릿으로 사용
        return "showoff/showlist";
    }
}