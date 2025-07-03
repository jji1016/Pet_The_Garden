package com.petthegarden.petthegarden.showoff.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShowRegController {
    @GetMapping("/showoff/showreg")
    public String showShowoffPage() {
        // showreg.html을 템플릿으로 사용
        return "showoff/showreg";
    }
}