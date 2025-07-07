package com.petthegarden.petthegarden.showoff.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/showoff")
@RequiredArgsConstructor
@Slf4j
public class ShowDetailController {

    @GetMapping("/showdetail/{petId}")
    public String showDetail(@PathVariable Long petId, Model model) {
        // 실제로는 서비스에서 petId로 상세 정보 조회
        // 예시: Pet pet = petService.findById(petId);
        // model.addAttribute("pet", pet);

        // 여기서는 예시 데이터 하드코딩
        model.addAttribute("petName", "피치");
        model.addAttribute("species", "포메라니안 개");
        model.addAttribute("owner", "피치집사냥냥");
        model.addAttribute("regDate", "2025.06.25");
        model.addAttribute("viewCount", 100);
        model.addAttribute("likeCount", 30);
        model.addAttribute("imagePath", "/images/웰시코기.png");
        model.addAttribute("content", "귀여운 피치의 장기자랑입니다!");

        return "showoff/showdetail";
    }
}
