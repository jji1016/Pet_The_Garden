package com.petthegarden.petthegarden.petnote;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.entity.Pet;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/petnote")
@RequiredArgsConstructor
public class PetnoteController {
    private final PetnoteService petnoteService;

    @GetMapping("/list")
    public String list() {
        return "petnote/list";
    }

    @GetMapping("/profile/{userID}")
    public String profile(@PathVariable("userID") String userID, Model model) {
        model.addAttribute("userID", userID);
//        model.addAttribute("memberDto", memberDto);
        return "petnote/profile";
    }




    @GetMapping("/diaryreg")
    public String diaryreg() {
        return "petnote/diaryreg";
    }

    @PostMapping("/diaryreg")
    public String diaryreg(@Valid @ModelAttribute("diaryDto") DiaryDto diaryDto, BindingResult bindingResult,
                           @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (bindingResult.hasErrors()) {
            return "petnote/diaryreg";
        }

//        Integer memberId = customUserDetails.getMemberId();
//        Integer petId = petnoteService.getFirstPetIdByMember(memberId);
//
//        diaryDto.setMemberId(memberId);
//        diaryDto.setPetId(petId);

        Integer memberId = customUserDetails.getMemberId();
        petnoteService.saveDiary(diaryDto, memberId);

        return "redirect:/petnote/profile";
    }
}
