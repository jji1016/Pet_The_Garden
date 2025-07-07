package com.petthegarden.petthegarden.stray;

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
@RequestMapping("/stray")
@RequiredArgsConstructor
@Slf4j
public class StrayController {
    private final StrayApiService strayApiService;
    private final StrayService strayService;


    @GetMapping("/protect")
    public String protect(@RequestParam(required = false) String startDate,
                          @RequestParam(required = false) String endDate,
                          @RequestParam(required = false) String discvryPlc,
                          @RequestParam(required = false) String species,
                          Model model) {
//        strayService.deleteAllInBatch(); // 기존 데이터 전부 삭제
//        strayService.refreshData(); //새로운 데이터 db에 저장

        // 서비스에서 API 호출해 데이터 받아오기 //이거 검색결과 기준으로 수정해야됨(서비스에 새롭게 만들기)
        List<StrayDto> strayList = strayApiService.fetchStrayData();
        log.info("strayList ===={}", strayList.toString());

        model.addAttribute("strayList", strayList);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("discvryPlc", discvryPlc);
        model.addAttribute("species", species);

        return "stray/protect";
    }

    @GetMapping("/detail/{abdmIdNtfyNo}")
    public String detail(@PathVariable String abdmIdNtfyNo, Model model) {

        return "stray/detail/" + abdmIdNtfyNo;
    }
}
