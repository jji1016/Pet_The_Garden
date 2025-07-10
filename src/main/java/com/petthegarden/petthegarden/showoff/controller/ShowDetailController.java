package com.petthegarden.petthegarden.showoff.controller;

import com.petthegarden.petthegarden.entity.ShowOff;
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

    @GetMapping("/showdetail/{showOffId}")
    public String showDetail(@PathVariable Integer showOffId,
                             Model model,
                             Principal principal) {
        ShowOff showOff = showListService.getShowOffById(showOffId);
        model.addAttribute("showOff", showOff);

        boolean isAuthor = false;
        if (principal != null && showOff.getMember() != null) {
            isAuthor = principal.getName().equals(showOff.getMember().getUserID());
        }
        model.addAttribute("isAuthor", isAuthor);

        return "showoff/showdetail";
    }
}
