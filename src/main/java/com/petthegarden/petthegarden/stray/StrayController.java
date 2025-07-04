package com.petthegarden.petthegarden.stray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stray")
public class StrayController {

    @GetMapping("/protect")
    public String protect() {
        return "stray/protect";
    }
}
