package com.petthegarden.petthegarden.showoff.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShowDetailController {

    @GetMapping("/showoff/showdetail")
    public String showDetailPage() {
        return "showoff/showdetail";
    }
}
