package com.petthegarden.petthegarden.showoff.controller;

import com.petthegarden.petthegarden.entity.ShowOff;
import com.petthegarden.petthegarden.entity.ShowOffComment;
import com.petthegarden.petthegarden.showoff.dto.ShowDetailDto;
import com.petthegarden.petthegarden.showoff.service.ShowDetailService;
import com.petthegarden.petthegarden.showoff.service.ShowOffCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/showoff")
public class ShowDetailController {

    private final ShowDetailService showDetailService;
    private final ShowOffCommentService showOffCommentService;

    @GetMapping("/showdetail/{id}")
    public String showDetail(
            @PathVariable Integer id,
            Model model,
            @AuthenticationPrincipal UserDetails principal
    ) {
        ShowDetailDto dto = showDetailService.getDetail(id);
        List<ShowOffComment> commentList = showOffCommentService.findByShowOffId(id);

        model.addAttribute("showOff", dto.getShowOff());
        model.addAttribute("youtubeId", dto.getYoutubeId());
        model.addAttribute("youtubeUrl", dto.getYoutubeUrl());
        model.addAttribute("isAuthor", dto.isAuthor());
        model.addAttribute("commentList", commentList);
        model.addAttribute("isLogin", principal != null);

        return "showoff/showdetail";
    }

    @PostMapping("/showdetail/{id}/comment")
    public String writeComment(
            @PathVariable Integer id,
            @RequestParam("content") String content,
            @AuthenticationPrincipal UserDetails principal
    ) {
        showOffCommentService.saveComment(id, content, principal);
        return "redirect:/showoff/showdetail/" + id;
    }
}
