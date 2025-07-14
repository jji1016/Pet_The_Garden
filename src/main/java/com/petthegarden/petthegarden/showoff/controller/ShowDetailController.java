package com.petthegarden.petthegarden.showoff.controller;

import com.petthegarden.petthegarden.entity.ShowOff;
import com.petthegarden.petthegarden.showoff.dto.ShowDetailDto;
import com.petthegarden.petthegarden.showoff.service.ShowDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/showoff")
public class ShowDetailController {

    private final ShowDetailService showDetailService;

    @GetMapping("/showdetail/{id}")
    public String showDetail(@PathVariable Integer id, Model model) {

        ShowDetailDto dto = showDetailService.getDetail(id);

        model.addAttribute("showOff", dto.getShowOff());
        model.addAttribute("youtubeId", dto.getYoutubeId());
        model.addAttribute("youtubeUrl", dto.getYoutubeUrl());
        model.addAttribute("isAuthor", dto.isAuthor());

        return "showoff/showdetail";
    }
}
