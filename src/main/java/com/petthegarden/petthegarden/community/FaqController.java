package com.petthegarden.petthegarden.community;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaqController {
    @GetMapping("/community/faq")
    public String showCommunityPage() {
        // faq.html을 템플릿으로 사용
        return "community/faq";
    }
}
