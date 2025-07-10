package com.petthegarden.petthegarden.showoff.controller;

import com.petthegarden.petthegarden.entity.ShowOff;
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

        List<ShowOff> popularShowOffs = showListService.getTop3PopularShowOffs();
        model.addAttribute("popularShowOffs", popularShowOffs);

        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by("regDate").descending());
        Page<ShowOff> showOffPage = showListService.getShowOffsByPage(pageRequest);

        model.addAttribute("showOffList", showOffPage.getContent());
        model.addAttribute("showOffPage", showOffPage);

        return "showoff/showlist";
    }
}
