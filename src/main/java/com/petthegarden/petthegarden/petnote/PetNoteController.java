package com.petthegarden.petthegarden.petnote;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/petnote")
@RequiredArgsConstructor
public class PetNoteController {
    private final PetnoteService petnoteService;

    @GetMapping("/list")
    public String list() {
        return "petnote/list";
    }

    @GetMapping("/profile")
    public String profile() {
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
        // 1. 로그인한 사용자 정보에서 Member 객체 추출
        String memberName = customUserDetails.getUsername(); // 또는 직접 조회

        // 2. 선택한 Pet ID를 통해 Pet 객체 조회
        Pet pet = petRepository.findById(diaryDto.getPetId())
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        // 3. DTO에 Member, Pet 세팅
        diaryDto.setMemberId(memberName);
        diaryDto.setPet(pet);

        petnoteService.save(diaryDto);
        return "redirect:/petnote/profile";
    }
}
