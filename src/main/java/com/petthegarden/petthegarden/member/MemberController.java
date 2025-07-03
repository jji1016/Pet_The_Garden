package com.petthegarden.petthegarden.member;

import com.petthegarden.petthegarden.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/signup")
    public String signup() {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute MemberDto memberDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "member/signup";
        }
        memberService.save(memberDto);
        return "member/signup";
    }

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }
}
