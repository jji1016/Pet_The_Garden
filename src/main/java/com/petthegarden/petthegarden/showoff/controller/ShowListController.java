package com.petthegarden.petthegarden.showoff.controller;

import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.showoff.service.ShowListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/showoff")
@RequiredArgsConstructor
public class ShowListController {
    private final ShowListService showListService;

    @GetMapping("/showlist")
    public String showList(Model model) {
        List<Pet> petList = showListService.getAllPets();
        model.addAttribute("petList", petList);

        // 인기 게시글(예시: 최근 등록된 3개)
        List<Pet> popularPets = petList.stream().limit(3).toList();
        model.addAttribute("popularPets", popularPets);

        return "showoff/showlist";
    }

    @GetMapping("/detail/{id}")
    public String petDetail(@PathVariable Integer id, Model model) {
        Pet pet = showListService.getPetById(id);
        model.addAttribute("pet", pet);
        return "showoff/detail";
    }
}
