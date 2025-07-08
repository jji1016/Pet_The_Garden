package com.petthegarden.petthegarden.mypage.controller;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.community.dto.BoardDto;
import com.petthegarden.petthegarden.community.service.CommunityService;
import com.petthegarden.petthegarden.constant.PetGender;
import com.petthegarden.petthegarden.entity.Board;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.mypage.dto.MemberDto;
import com.petthegarden.petthegarden.mypage.dto.PetDto;
import com.petthegarden.petthegarden.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
@Slf4j
@RequiredArgsConstructor
public class MyPageController {
    private final MypageService mypageService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CommunityService communityService;

    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model,
                         Pageable pageable){
        if (customUserDetails == null) {
            // 비로그인 시 로그인 페이지로 리다이렉트
            return "redirect:/member/login";
        }

        String userID = customUserDetails.getUsername(); //로그인한 유저의 아이디
        Integer loggedMemberID = customUserDetails.getLoggedMember().getId(); //member테이블 PK(memberID)
        MemberDto loggedMemberDto = mypageService.findByUserID(userID); //로그인한 유저의 정보들
        log.info("loggedMemberDto: {}", loggedMemberDto);
        List<Board> myBoards = communityService.getBoardList2(loggedMemberID); //로그인한 유저의 게시판정보
        List<PetDto> pets = mypageService.findPetsByMemberId(loggedMemberDto.getId()); //로그인한 유저의 펫정보
        Page<Board> myBoardsPage = communityService.getBoardsByMember(loggedMemberID, pageable);

        //MemberDto loggedMemberDto = mypageService.findByUserID("admin"); // admin 계정 강제 지정
        //List<Board> myBoards = communityService.getBoardList2(1); //admin 계정 게시판 정보 불러옴
        //List<PetDto> pets = mypageService.findPetsByMemberId(1);

        model.addAttribute("loggedMemberDto", loggedMemberDto);
        model.addAttribute("myBoards", myBoards);
        model.addAttribute("pets", pets);
        model.addAttribute("myBoards", myBoardsPage.getContent()); // 7개씩 나오는 게시글 리스트
        model.addAttribute("myBoardsPage", myBoardsPage); // 페이지 정보

        return "mypage/mypage";
    }

    @PostMapping("/info") //회원 정보 조회
    @ResponseBody
    public MemberDto info(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String userID = customUserDetails.getUsername(); //로그인한 유저의 아이디
        MemberDto loggedMemberDto = mypageService.findByUserID(userID); //로그인한 유저의 정보들
        //MemberDto loggedMemberDto = mypageService.findByUserID("admin");
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

        String userID = customUserDetails.getUsername();
        MemberDto loggedMemberDto = mypageService.findByUserID(userID);
        //MemberDto loggedMemberDto = mypageService.findByUserID("admin");

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
    @GetMapping("/petreg") //pet등록페이지
    public String showPetReg() {
        return "mypage/petreg";
    }

    @PostMapping("/petreg") //pet등록
    public String registerPet(
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
            // 임시로 id가 302인 어드민 회원 가져오기 (멤버 아이디가 1인 경우)
            member = mypageService.findById(1);
        } else {
            member = mypageService.findMemberByUserID(userDetails.getUsername());
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

        return "redirect:/mypage/mypage";
    }

    @PostMapping("/upload-image") //ck 업로드
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam("upload") MultipartFile upload) {
        return mypageService.uploadImage(upload);
    }

    @PostMapping("/petdelete/{id}") //pet등록 삭제처리
    public String deletePet(@PathVariable("id") Integer petId,
                            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return "redirect:/login";  // 로그인 필요시 로그인 페이지로
        }
        Member member = mypageService.findMemberByUserID(userDetails.getUsername());
        //Member member = mypageService.findByUserID("admin").toMember();

        boolean deleted = mypageService.deletePet(petId, member);

        // 삭제 성공하면 마이페이지로 리다이렉트
        return "redirect:/mypage/mypage";
    }
}
