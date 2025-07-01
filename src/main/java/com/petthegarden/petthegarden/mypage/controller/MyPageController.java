package com.petthegarden.petthegarden.mypage.controller;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.constant.PetGender;
import com.petthegarden.petthegarden.constant.Role;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.mypage.dto.MemberDto;
import com.petthegarden.petthegarden.mypage.dto.PetDto;
import com.petthegarden.petthegarden.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        //String userID = customUserDetails.getUsername(); //로그인한 유저의 아이디
        //Integer loggedMemberID = customUserDetails.getLoggedMember().getId(); //member테이블 PK(memberID)
        //MemberDto loggedMemberDto = mypageService.findByUserID(userID); //로그인한 유저의 정보들
        //log.info("loggedMemberDto: {}", loggedMemberDto);

        MemberDto loggedMemberDto = mypageService.findByUserID("admin"); // admin 계정 강제 지정

        model.addAttribute("loggedMemberDto", loggedMemberDto);

        return "mypage/mypage";
    }

    @PostMapping("/info") //회원 정보 조회
    @ResponseBody
    public MemberDto info(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        //String userID = customUserDetails.getUsername(); //로그인한 유저의 아이디
        //MemberDto loggedMemberDto = mypageService.findByUserID(userID); //로그인한 유저의 정보들
        MemberDto loggedMemberDto = mypageService.findByUserID("admin");
        log.info("MemberDto 반환값: {}", loggedMemberDto);
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
        //String newEmail = data.get("userEmail");
        //String newZipcode = data.get("zipcode");
        //String newAddress01 = data.get("address01");
        //String newAddress02 = data.get("address02");
        //String newTel = data.get("tel");

        //String userID = customUserDetails.getUsername();
        //MemberDto loggedMemberDto = mypageService.findByUserID(userID);
        MemberDto loggedMemberDto = mypageService.findByUserID("admin");

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

        //if (newEmail != null) loggedMemberDto.setUserEmail(newEmail);
        //if (newTel != null) loggedMemberDto.setTel(newTel);
        //if (newZipcode != null) loggedMemberDto.setZipcode(newZipcode);
        //if (newAddress01 != null) loggedMemberDto.setAddress01(newAddress01);
        //if (newAddress02 != null) loggedMemberDto.setAddress02(newAddress02);
        //log.info("modify_loggedMemberDto: {}", loggedMemberDto);
        if (data.get("userEmail") != null) loggedMemberDto.setUserEmail(data.get("userEmail"));
        if (data.get("tel") != null) loggedMemberDto.setTel(data.get("tel"));
        if (data.get("zipcode") != null) loggedMemberDto.setZipcode(data.get("zipcode"));
        if (data.get("address01") != null) loggedMemberDto.setAddress01(data.get("address01"));
        if (data.get("address02") != null) loggedMemberDto.setAddress02(data.get("address02"));

        mypageService.updateInfo(loggedMemberDto);
        result.put("isModify", "true");
        return result;
    }

    @PostMapping("/petreg")
    public ResponseEntity<String> registerPet(
            @RequestParam("petName") String petName,
            @RequestParam("species") String species,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("character") String character,
            @RequestParam("petLike") String petLike,
            @RequestParam("petDisLike") String petDisLike,
            @RequestParam("content") String content,
            @RequestParam("petGender") String petGender,
            @RequestParam("profileImg") MultipartFile file,
            @AuthenticationPrincipal UserDetails userDetails) {

        // 1. 현재 로그인한 사용자 정보 가져오기
        Member member;

        if (userDetails == null) {
            // 임시로 id가 302인 어드민 회원 가져오기 (멤버 아이디가 302인 경우)
            member = mypageService.findById(302);
        } else {
            member = mypageService.findByUserID(userDetails.getUsername()).toMember();
        }

        // 2. PetDto 객체로 변환
        PetDto petDto = PetDto.builder()
                .petName(petName)
                .species(species)
                .birthDate(birthDate)
                .character(character)
                .petLike(petLike)
                .petDisLike(petDisLike)
                .content(content)
                .petGender(PetGender.valueOf(petGender))
                .build();

        // 3. 저장
        mypageService.petreg(petDto, member, file);

        return ResponseEntity.ok("등록 성공");
    }
}
