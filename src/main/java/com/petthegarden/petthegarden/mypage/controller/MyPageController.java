package com.petthegarden.petthegarden.mypage.controller;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.constant.Role;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.mypage.dto.MemberDto;
import com.petthegarden.petthegarden.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
@Slf4j
@RequiredArgsConstructor
public class MyPageController {
    private final MypageService mypageService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    public String mypage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model){
        String userID = customUserDetails.getUsername(); //로그인한 유저의 아이디
        Integer loggedMemberID = customUserDetails.getLoggedMember().getId(); //member테이블 PK(memberID)
        MemberDto loggedMemberDto = mypageService.findByUserID(userID); //로그인한 유저의 정보들
        log.info("loggedMemberDto: {}", loggedMemberDto);


        model.addAttribute("loggedMemberDto", loggedMemberDto);

        return "mypage/mypage";
    }

    @PostMapping("/info") //회원 정보 조회
    @ResponseBody
    public MemberDto info(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String userID = customUserDetails.getUsername(); //로그인한 유저의 아이디
        MemberDto loggedMemberDto = mypageService.findByUserID(userID); //로그인한 유저의 정보들

        return loggedMemberDto;
    }

    @PostMapping("/modify")
    @ResponseBody
    public Map<String, String> modify(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                      @RequestBody Map<String, String> data) {
        Map<String, String> result = new HashMap<>();

        String currentPassword = data.get("currentPassword");
        String password = data.get("password"); // 새 비밀번호
        String password2 = data.get("password2"); // 새 비밀번호 확인
        String newEmail = data.get("userEmail");
        String newZipcode = data.get("zipcode");
        String newAddress01 = data.get("address01");
        String newAddress02 = data.get("address02");
        String newTel = data.get("tel");

        String userID = customUserDetails.getUsername();
        MemberDto loggedMemberDto = mypageService.findByUserID(userID);

        log.info("loggedMemberDto: {}", loggedMemberDto);
        // 비밀번호 검증
        if (password != null && !password.isBlank()) {
            if (!bCryptPasswordEncoder.matches(currentPassword, loggedMemberDto.getUserPW())) {
                result.put("isModify", "false");
                result.put("error", "현재 비밀번호가 올바르지 않습니다.");
                return result;
            }

            if (!password.equals(password2)) {
                result.put("isModify", "false");
                result.put("error", "비밀번호 확인이 일치하지 않습니다.");
                return result;
            }

            String encodeUserPW = bCryptPasswordEncoder.encode(password);
            loggedMemberDto.setUserPW(encodeUserPW);
        } else {
            // 비밀번호 변경 안 하는 경우 기존 비밀번호 유지
            loggedMemberDto.setUserPW(customUserDetails.getPassword());
        }

        if (newEmail != null) loggedMemberDto.setUserEmail(newEmail);
        if (newTel != null) loggedMemberDto.setTel(newTel);
        if (newZipcode != null) loggedMemberDto.setZipcode(newZipcode);
        if (newAddress01 != null) loggedMemberDto.setAddress01(newAddress01);
        if (newAddress02 != null) loggedMemberDto.setAddress02(newAddress02);
        log.info("modify_loggedMemberDto: {}", loggedMemberDto);
        mypageService.updateInfo(loggedMemberDto);
        result.put("isModify", "true");
        return result;
    }

}
