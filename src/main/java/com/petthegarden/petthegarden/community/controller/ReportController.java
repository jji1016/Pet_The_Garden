package com.petthegarden.petthegarden.community.controller;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.community.dto.ReportDto;
import com.petthegarden.petthegarden.community.service.ReportService;
import com.petthegarden.petthegarden.entity.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/board/{id}/report")
    @ResponseBody
    public ResponseEntity<?> reportBoardJson(@PathVariable Integer id,
                                             @RequestBody ReportDto reportDto,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인 후 신고할 수 있습니다.");
        }

        Member loginUser = userDetails.getLoggedMember();

        try {
            reportDto.setRepID(id);
            reportDto.setType("BOARD");
            reportService.report(reportDto, loginUser);
            return ResponseEntity.ok("게시글 신고가 접수되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/comment/{id}/report")
    @ResponseBody
    public ResponseEntity<?> reportCommentJson(@PathVariable Integer id,
                                               @RequestBody ReportDto reportDto,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인 후 신고할 수 있습니다.");
        }


        Member loginUser = userDetails.getLoggedMember();

        try {
            reportDto.setRepID(id);
            reportDto.setType("BOARDCOMMENT");
            reportService.report(reportDto, loginUser);
            return ResponseEntity.ok("댓글 신고가 접수되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // "이미 신고한 항목입니다."
        }
    }

}