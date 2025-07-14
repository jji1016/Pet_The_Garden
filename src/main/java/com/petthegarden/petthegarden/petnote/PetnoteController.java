package com.petthegarden.petthegarden.petnote;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.member.MemberService;
import com.petthegarden.petthegarden.petnote.dto.DiaryDto;
import com.petthegarden.petthegarden.petnote.dto.PetDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/petnote")
@RequiredArgsConstructor
public class PetnoteController {
    private final PetnoteService petnoteService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String list() {
        return "petnote/list";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                          Model model) {

        String userID = customUserDetails.getLoggedMember().getUserID();
        Integer memberID = customUserDetails.getLoggedMember().getId();
        List<Pet> petList = petnoteService.getPetList(memberID);
        PetDto firstPetDto = petnoteService.getPetDto(memberID);
        System.out.println("PetnoteContoller 펫디티오 " + firstPetDto);

        model.addAttribute("userID", userID);
        model.addAttribute("petList", petList);
        model.addAttribute("firstPetDto", firstPetDto);

        return "petnote/profile";
    }


//    @GetMapping("/diaryreg/{petName}")
//    public String diaryreg(@PathVariable String petName, Model model) {
//        String petName = firstPetDto.getPetName();
//        return "petnote/diaryreg";
//    }

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
