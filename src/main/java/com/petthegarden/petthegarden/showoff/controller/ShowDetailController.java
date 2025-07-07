package com.petthegarden.petthegarden.showoff.controller;

import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.showoff.service.ShowListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/showoff")
@RequiredArgsConstructor
@Slf4j
public class ShowDetailController {

    private final ShowListService showListService;

    @GetMapping("/showdetail/{petId}")
    public String showDetail(@PathVariable Integer petId,
                             Model model,
                             Principal principal) {
        Pet pet = showListService.getPetById(petId);
        model.addAttribute("pet", pet);

        boolean isAuthor = false;
        if (principal != null && pet.getMember() != null) {
            isAuthor = principal.getName().equals(pet.getMember().getUserID());
        }
        model.addAttribute("isAuthor", isAuthor);

        return "showoff/showdetail";
    }
}
