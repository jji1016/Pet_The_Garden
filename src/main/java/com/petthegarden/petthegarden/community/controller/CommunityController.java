package com.petthegarden.petthegarden.community.controller;


import com.petthegarden.petthegarden.community.service.CommunityService;
import com.petthegarden.petthegarden.entity.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/community")
@Slf4j
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;

    @GetMapping
    public String community(Model model, @RequestParam(required = false) String keyword) {
        List<Board> boards = communityService.getBoardList();
        model.addAttribute("boards", boards);
        model.addAttribute("keyword", keyword);
        return "/community/board";
    }

    @GetMapping("/boarddetail/{id}")
    public String boardDetail(@PathVariable Integer id, Model model) {
        Board board = communityService.getBoardById(id);
        model.addAttribute("board", board);
        return "/community/boarddetail";
    }
}
