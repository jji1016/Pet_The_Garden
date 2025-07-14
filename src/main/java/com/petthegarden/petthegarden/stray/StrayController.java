package com.petthegarden.petthegarden.stray;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.lang.Math.ceil;

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
                          @RequestParam(defaultValue = "1") int currentPage,
                          Model model) {
//        strayService.deleteAllInBatch(); // 기존 데이터 전부 삭제
//        strayService.refreshData(); //새로운 데이터 db에 저장

        startDate = startDate == null ? "" : startDate;
        endDate = endDate == null ? "" : endDate;
        discvryPlc = discvryPlc == null ? "" : discvryPlc.replaceAll("\\s+"," ").trim();
        species = species == null ? "" : species.replaceAll("\\s+", " ").trim();

        //startDate가 endDate보다 클 경우 서로 교체
        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            if (start.isAfter(end)) {
                String temp = startDate;
                startDate = endDate;
                endDate = temp;
            }
        }

        int itemsPerPage = 20; // 한 페이지당 최대 게시물 수
        int totalStray = strayService.totalStray(startDate,endDate,discvryPlc,species); //그냥 혹은 검색시 나오는 게시물 수
        int totalPage = (int) Math.ceil((double) totalStray / itemsPerPage);
        totalPage = totalPage == 0 ? 1 : totalPage;
        int startItem = (currentPage - 1) * itemsPerPage + 1;
        int endItem = currentPage * itemsPerPage;

        List<StrayDto> strayList = strayService.getStrayList(startDate,endDate,discvryPlc,species,startItem,endItem);
        log.info("strayList ===={}", strayList.toString());

        model.addAttribute("strayList", strayList);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("discvryPlc", discvryPlc);
        model.addAttribute("species", species);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPage", totalPage);

        return "stray/protect";
    }

    @GetMapping("/detail/{ABDM_IDNTFY_NO}")
    public String detail(@PathVariable String ABDM_IDNTFY_NO, Model model) {
        StrayDto detailInfo = strayService.getDetailInfo(ABDM_IDNTFY_NO);
        log.info("detailInfo ===={}", detailInfo.toString());

        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedBeginDate = "";
        String formattedEndDate = "";

        try {
            if (detailInfo.getPblancBeginDe() != null) {
                formattedBeginDate = LocalDate.parse(detailInfo.getPblancBeginDe(), inputFormat).format(outputFormat);
            }
            if (detailInfo.getPblancEndDe() != null) {
                formattedEndDate = LocalDate.parse(detailInfo.getPblancEndDe(), inputFormat).format(outputFormat);
            }
        } catch (Exception e) {
            log.warn("날짜 파싱 오류", e);
        }

        model.addAttribute("detailInfo", detailInfo);
        model.addAttribute("formattedBeginDate", formattedBeginDate);
        model.addAttribute("formattedEndDate", formattedEndDate);

        return "stray/detail";
    }
}
