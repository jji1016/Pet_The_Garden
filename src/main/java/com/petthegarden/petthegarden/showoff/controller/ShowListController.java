package com.petthegarden.petthegarden.showoff.controller;// ShowListController.java
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

        List<Pet> popularPets = showListService.getTop3PopularPets();
        model.addAttribute("popularPets", popularPets);

        return "showoff/showlist";
    }
}
