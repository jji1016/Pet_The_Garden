package com.petthegarden.petthegarden.member;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.member.dto.MemberDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signup(Model model) {
        MemberDto memberDto = new MemberDto();
        model.addAttribute("memberDto", memberDto);
        System.out.println("✅ memberDto: " + memberDto);
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("memberDto") MemberDto memberDto, BindingResult bindingResult) {
        System.out.println("받은 값: " + memberDto);
        System.out.println("유효성 에러 있음? " + bindingResult.hasErrors());
        if (bindingResult.hasErrors()) {
            return "member/signup";
        }
        memberService.save(memberDto);
        return "redirect:/member/login";
    }

    @GetMapping("/check-id")
    @ResponseBody
    public boolean checkIdDuplicate(@RequestParam String userID) {
        return memberService.existsByUserID(userID);
    }

    @GetMapping("/check-name")
    @ResponseBody
    public boolean checkNameDuplicate(@RequestParam String userName) {
        return memberService.existsByUserName(userName);
    }

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/member/info")
    public String memberInfo(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Member member = customUserDetails.getLoggedMember();
        model.addAttribute("member", member);
        return "member/info";
    }
}
