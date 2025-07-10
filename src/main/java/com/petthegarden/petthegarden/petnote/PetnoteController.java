package com.petthegarden.petthegarden.petnote;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.petnote.dto.DiaryDto;
import com.petthegarden.petthegarden.petnote.dto.PetDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/petnote")
@RequiredArgsConstructor
public class PetnoteController {

    private final PetnoteService petnoteService;

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


    @GetMapping("/diaryreg/{petID}")
    public String diaryreg(Model model) {
        model.addAttribute("diaryDto", new DiaryDto());
        return "petnote/diaryreg";
    }


    @PostMapping("/diaryreg")
    public String diaryWrite(@Valid @ModelAttribute("diaryDto") DiaryDto diaryDto,
                             BindingResult bindingResult,
                             @AuthenticationPrincipal CustomUserDetails customUserDetails,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("diaryDto", diaryDto);
            return "petnote/diaryreg";
        }

        Member member = customUserDetails.getLoggedMember();
        Integer memberID = customUserDetails.getLoggedMember().getId();
        Pet pet = petnoteService.findFirstPet(memberID);

        diaryDto.setMember(member);
        diaryDto.setPet(pet);

        petnoteService.diarySave(diaryDto);
        return "redirect:/petnote/diaryreg";
    }


    @PostMapping("/diaryreg/upload-image")
    @ResponseBody
    public Map<String, String> uploadImage(@RequestParam("upload") MultipartFile uploadFile) throws IOException {
        String imageUrl = petnoteService.uploadImg(uploadFile);
        Map<String, String> response = new HashMap<>();
        response.put("url", imageUrl);
        return response;
    }

}
