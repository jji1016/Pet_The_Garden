package com.petthegarden.petthegarden.showoff.controller;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.ShowOff;
import com.petthegarden.petthegarden.showoff.dto.CorrectDto;
import com.petthegarden.petthegarden.showoff.service.CorrectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/showoff")
@RequiredArgsConstructor
@Slf4j
public class CorrectController {

    private final CorrectService correctService;

    // 기존 PathVariable 방식
    @GetMapping("/correct/{id}")
    public String showCorrectForm(@PathVariable Integer id,
                                  Model model,
                                  @AuthenticationPrincipal CustomUserDetails user) {

        ShowOff showOff = correctService.getShowOffById(id);
        if (!showOff.getMember().getId().equals(user.getLoggedMember().getId())) {
            return "redirect:/showoff/showdetail/" + id;
        }
        model.addAttribute("correctDto", CorrectDto.fromEntity(showOff));
        return "showoff/correct";
    }

    // 쿼리 파라미터 방식 추가
    @GetMapping("/correct")
    public String showCorrectFormByQuery(@RequestParam Integer id,
                                         Model model,
                                         @AuthenticationPrincipal CustomUserDetails user) {
        // 내부에서 기존 메서드 재사용
        return showCorrectForm(id, model, user);
    }

    @PostMapping("/correct/{id}")
    public String doCorrect(@PathVariable Integer id,
                            @Valid @ModelAttribute CorrectDto correctDto,
                            BindingResult bindingResult,
                            RedirectAttributes ra,
                            @AuthenticationPrincipal CustomUserDetails user,
                            Model model) {

        if (bindingResult.hasErrors()) {
            ShowOff showOff = correctService.getShowOffById(id);
            model.addAttribute("correctDto", CorrectDto.fromEntity(showOff));
            return "showoff/correct";
        }

        Member member = user.getLoggedMember();

        try {
            ShowOff updated = correctService.updateShowOff(id, correctDto, member);
            ra.addFlashAttribute("message", "수정이 완료되었습니다!");
            return "redirect:/showoff/showdetail/" + updated.getId();
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("correctDto", correctDto);
            return "showoff/correct";
        }
    }
}
