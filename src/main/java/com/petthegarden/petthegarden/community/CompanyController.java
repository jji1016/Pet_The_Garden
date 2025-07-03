package com.petthegarden.petthegarden.community;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompanyController {
    @GetMapping("/community/company")
    public String showCommunityPage() {
        // company.html을 템플릿으로 사용
        return "community/company";
    }
}