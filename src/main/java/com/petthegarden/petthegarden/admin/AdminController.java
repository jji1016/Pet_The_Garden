package com.petthegarden.petthegarden.admin;

import com.petthegarden.petthegarden.admin.dto.AdminShowOffDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
