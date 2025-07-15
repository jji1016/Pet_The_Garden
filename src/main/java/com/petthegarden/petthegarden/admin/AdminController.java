package com.petthegarden.petthegarden.admin;

import com.petthegarden.petthegarden.admin.dto.AdminMemberDto;
import com.petthegarden.petthegarden.admin.dto.AdminPetDto;
import com.petthegarden.petthegarden.admin.dto.AdminReportDto;
import com.petthegarden.petthegarden.admin.dto.AdminShowOffDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        /* 상단 */
        int newMember = adminService.newMember();
        int totalMember = adminService.totalMember();
        int totalPet = adminService.totalPet();

        /* 하단 */
        int recentPet = adminService.recentPet();
        int recentStray = adminService.recentStray();
        int recentShowOff = adminService.recentShowOff();
        int recentBoard = adminService.recentBoard();
        List<AdminShowOffDto> showOffList = adminService.getShowOffList();

        log.info("showOffList == {}",showOffList.toString());

        model.addAttribute("newMember", newMember);
        model.addAttribute("totalMember", totalMember);
        model.addAttribute("totalPet", totalPet);

        model.addAttribute("recentPet", recentPet);
        model.addAttribute("recentStray", recentStray);
        model.addAttribute("recentShowOff", recentShowOff);
        model.addAttribute("recentBoard", recentBoard);
        model.addAttribute("showOffList", showOffList);

        return "admin/dashboard";
    }

    @GetMapping("/memberList")
    public String memberList(Model model,
                             @RequestParam(required = false) String startDate,
                             @RequestParam(required = false) String endDate,
                             @RequestParam(defaultValue = "userID") String key,
                             @RequestParam(required = false) String search,
                             @RequestParam(defaultValue = "1") int currentPage) {
        startDate = startDate == null ? "" : startDate;
        endDate = endDate == null ? "" : endDate;
        search = search == null ? "" : search.replaceAll("\\s+"," ").trim();


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

        Pageable pageable = PageRequest.of(currentPage-1, 10, Sort.by("regDate").descending());

        Page<AdminMemberDto> memberList = adminService.getMemberList(startDate,endDate,key,search,pageable);
        log.info("memberList == {}",memberList.getContent());
        log.info("totalPage == {}",memberList.getTotalPages());
        int totalPage = memberList.getTotalPages();
        totalPage = totalPage == 0 ? 1 : totalPage;

        model.addAttribute("memberList", memberList.getContent());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("key", key);
        model.addAttribute("search", search);

        return "admin/memberList";
    }

    @GetMapping("/petList")
    public String petList(Model model,
                             @RequestParam(required = false) String startDate,
                             @RequestParam(required = false) String endDate,
                             @RequestParam(defaultValue = "petName") String key,
                             @RequestParam(required = false) String search,
                             @RequestParam(defaultValue = "1") int currentPage) {

        startDate = startDate == null ? "" : startDate;
        endDate = endDate == null ? "" : endDate;
        search = search == null ? "" : search.replaceAll("\\s+"," ").trim();


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

        Pageable pageable = PageRequest.of(currentPage-1, 7, Sort.by("regDate").descending());

        Page<AdminPetDto> petList = adminService.getPetList(startDate,endDate,key,search,pageable);
        log.info("petList == {}",petList.getContent());
        log.info("totalPage == {}",petList.getTotalPages());
        int totalPage = petList.getTotalPages();
        totalPage = totalPage == 0 ? 1 : totalPage;

        model.addAttribute("petList", petList.getContent());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("key", key);
        model.addAttribute("search", search);

        return "admin/petList";
    }

    @GetMapping("/report")
    public String report(Model model,
                             @RequestParam(required = false) String startDate,
                             @RequestParam(required = false) String endDate,
                             @RequestParam(defaultValue = "userID") String key,
                             @RequestParam(required = false) String search,
                             @RequestParam(defaultValue = "1") int currentPage) {
        startDate = startDate == null ? "" : startDate;
        endDate = endDate == null ? "" : endDate;
        search = search == null ? "" : search.replaceAll("\\s+"," ").trim();


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

        Pageable pageable = PageRequest.of(currentPage-1, 10, Sort.by("reportDate").descending());

        Page<AdminReportDto> reportList = adminService.getReportList(startDate,endDate,key,search,pageable);
        log.info("memberList == {}",reportList.getContent());
        log.info("totalPage == {}",reportList.getTotalPages());
        int totalPage = reportList.getTotalPages();
        totalPage = totalPage == 0 ? 1 : totalPage;

        model.addAttribute("reportList", reportList.getContent());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("key", key);
        model.addAttribute("search", search);

        return "admin/report";
    }

    @GetMapping("/sample")
    public String sample(Model model) {
        return "admin/sample";
    }
}
