package com.petthegarden.petthegarden.showoff.controller;

import com.petthegarden.petthegarden.entity.ShowOff;
import com.petthegarden.petthegarden.showoff.service.ShowListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/showoff")
public class ShowListController {

    private final ShowListService showListService;

    @GetMapping("/showlist")
    public String showList(
            @PageableDefault(size = 5, sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "memberId", required = false) Integer memberId,
            Model model
    ) {
        // 인기 게시글 3개 가져오기 (좋아요 순으로 정렬)
        List<ShowOff> popularPosts = showListService.getPopularPosts();

        // 전체 게시글 페이징 처리
        Page<ShowOff> showOffPage;
        if (memberId != null) {
            showOffPage = showListService.getShowOffsByMember(memberId, pageable);
        } else {
            showOffPage = showListService.getAllShowOffs(pageable);
        }

        model.addAttribute("popularPosts", popularPosts);
        model.addAttribute("showOffPage", showOffPage);
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("totalPages", showOffPage.getTotalPages());
        model.addAttribute("memberId", memberId);

        return "showoff/showlist";
    }
}
