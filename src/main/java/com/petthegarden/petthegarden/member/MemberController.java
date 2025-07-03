package com.petthegarden.petthegarden.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

//    @PostMapping("/signup")
//    public String signup(@ModelAttribute MemberDto memberDto, BindingResult bindingResult) {
//        if(bindingResult.hasErrors()) {
//            return "member/signup";
//        }
//        Member member = memberService.save(memberDto);
//        if(member != null) {
//            return "redirect:/index/index";
//        }
//        return "member/signup";
//    }
}
