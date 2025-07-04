package com.petthegarden.petthegarden.stray;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/stray")
@RequiredArgsConstructor
@Slf4j
public class StrayController {
    private final StrayApiService strayApiService;


    @GetMapping("/protect")
    public String protect(Model model) {
        // 서비스에서 API 호출해 데이터 받아오기
        List<StrayDto> strayList = strayApiService.fetchStrayData();
        log.info("strayList ===={}",strayList.toString());

        // 모델에 담아서 뷰에 넘기기
        model.addAttribute("strayList", strayList);

        return "stray/protect";
    }
}
