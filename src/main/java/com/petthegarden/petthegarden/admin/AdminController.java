package com.petthegarden.petthegarden.admin;

import com.petthegarden.petthegarden.admin.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        /* 중단 */
        AdminSpeciesChart speciesChart = adminService.getSpeciesChart();

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

        model.addAttribute("species", speciesChart.getSpecies());
        model.addAttribute("counts", speciesChart.getCounts());

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

    @GetMapping("/memberDetail/{memberID}")
    public String sample(Model model,
                         @PathVariable String memberID) {
        Integer ID = Integer.parseInt(memberID);

        /* 해당 회원 정보 */
        AdminMemberDetailDto memberDetail = adminService.findById(ID);
        /* 회원의 반려동물 */
        List<AdminPetDto> petList = adminService.findByMember_Id(ID);
        /* 회원의 장기자랑 게시글, 댓글 */
        List<AdminShowOffBoardDto> showOffBoardList = adminService.getShowOffBoardList(ID);
        List<AdminShowOffCommentDto> showOffCommentList = adminService.getShowOffCommentList(ID);
        /* 회원의 자유게시판 게시글, 댓글 */
        List<AdminFreeBoardDto> freeBoardList = adminService.getFreeBoardList(ID);
        List<AdminFreeCommentDto> freeCommentList = adminService.getFreeCommentList(ID);


        log.info("memberDetail == {}",memberDetail);
        log.info("petList == {}",petList);
        log.info("showOffBoardList == {}",showOffBoardList);
        log.info("showOffCommentList == {}",showOffCommentList);
        log.info("freeBoardList == {}",freeBoardList);
        log.info("freeCommentList == {}",freeCommentList);

        model.addAttribute("memberDetail", memberDetail);
        model.addAttribute("petList", petList);
        model.addAttribute("showOffBoardList", showOffBoardList);
        model.addAttribute("showOffCommentList", showOffCommentList);
        model.addAttribute("freeBoardList", freeBoardList);
        model.addAttribute("freeCommentList", freeCommentList);

        return "admin/memberDetail";
    }

    @DeleteMapping("/deleteMember/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable Integer memberId) {
        // 삭제 로직
        adminService.deleteByMemberId(memberId);

        return ResponseEntity.ok("삭제 완료");
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
                         @RequestParam(required = false) LocalDate startDate,
                         @RequestParam(required = false) LocalDate endDate,
                         @RequestParam(defaultValue = "reporter") String key,
                         @RequestParam(defaultValue = "FREE_POST") String type,
                         @RequestParam(required = false) String search,
                         @RequestParam(defaultValue = "1") int currentPage) {
        search = search == null ? "" : search.replaceAll("\\s+"," ").trim();
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            LocalDate temp = startDate;  // LocalDate끼리 교환
            startDate = endDate;
            endDate = temp;
        }

        Pageable pageable = PageRequest.of(currentPage-1, 10, Sort.by("reportDate").descending());

        log.info("startDate == {}",startDate);
        log.info("endDate == {}",endDate);
        log.info("type == {}",type);
        log.info("key == {}",key);
        log.info("search == {}",search);

        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? endDate.atTime(LocalTime.MAX) : null;

        Page<AdminReportDto> reportList = adminService.getReportList(type, startDateTime, endDateTime, key, search, pageable);
        log.info("reportList == {}",reportList.getContent());
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
        model.addAttribute("type", type);

        return "admin/report";
    }

    @DeleteMapping("report/delete/{reportId}")
    @ResponseBody
    public ResponseEntity<String> deleteReport(@PathVariable Integer reportId) {
        try {
            // 예: 서비스 호출해서 삭제 처리
            adminService.deleteById(reportId);

            return ResponseEntity.ok("삭제 성공");
        } catch (Exception e) {
            // 오류 발생 시 상태 코드 500과 메시지 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
        }
    }



}
