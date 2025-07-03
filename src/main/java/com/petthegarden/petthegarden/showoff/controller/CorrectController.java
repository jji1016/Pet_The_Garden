package com.petthegarden.petthegarden.showoff.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CorrectController {
    @GetMapping("/showoff/correct")
    public String showShowoffPage() {
        // correct.html을 템플릿으로 사용
        return "showoff/correct";
    }
}
