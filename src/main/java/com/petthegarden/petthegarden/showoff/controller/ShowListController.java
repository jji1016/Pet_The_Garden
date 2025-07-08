package com.petthegarden.petthegarden.showoff.controller;

import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.showoff.service.ShowListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/showoff")
@RequiredArgsConstructor
public class ShowListController {
    private final ShowListService showListService;

    @GetMapping("/showlist")
    public String showList(@RequestParam(defaultValue = "0") int page, Model model) {
        // 페이지 번호가 0보다 작으면 무조건 0으로 보정
        if (page < 0) page = 0;

        List<Pet> popularPets = showListService.getTop3PopularPets();
        model.addAttribute("popularPets", popularPets);

        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by("regDate").descending());
        Page<Pet> petPage = showListService.getPetsByPage(pageRequest);
        model.addAttribute("petList", petPage.getContent());
        model.addAttribute("petPage", petPage);

        return "showoff/showlist";
    }
}
