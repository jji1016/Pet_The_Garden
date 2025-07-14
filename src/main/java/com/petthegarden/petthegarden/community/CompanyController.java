package com.petthegarden.petthegarden.community;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/community")
public class CompanyController {

    @GetMapping("/company")
    public String company() {
        return "community/company"; // Thymeleaf template path
    }
}
