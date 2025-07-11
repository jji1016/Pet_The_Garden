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


    @PostMapping("/profile/{petID}")
    public String changePetProfile(@PathVariable String petID,
                                   @RequestParam("petID") Integer ID,
                                   Model model,
                                   @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Integer memberID = customUserDetails.getLoggedMember().getId();
        List<Pet> petList = petnoteService.getPetList(memberID);
        PetDto selectedPetDto = petnoteService.getPetDtoByPetID(ID);

        model.addAttribute("petID", petID);
        model.addAttribute("petList", petList);
        model.addAttribute("firstPetDto", selectedPetDto);
        return "petnote/profile";
    }



    @GetMapping("/diaryreg/{petID}")
    public String diaryreg(@PathVariable Integer petID,
                           @AuthenticationPrincipal CustomUserDetails customUserDetails,
                           Model model) {
        Integer memberID = customUserDetails.getLoggedMember().getId();
        String petName = petnoteService.findFirstPet(memberID).getPetName();

        model.addAttribute("diaryDto", new DiaryDto());
        model.addAttribute("petID", petID);
        model.addAttribute("petName", petName);
        return "petnote/diaryreg";
    }


    @PostMapping("/diaryreg")
    public String diaryWrite(@Valid @ModelAttribute("diaryDto") DiaryDto diaryDto,
                             BindingResult bindingResult,
                             @AuthenticationPrincipal CustomUserDetails customUserDetails,
                             @RequestParam("petID") Integer petID,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("diaryDto", diaryDto);
            return "petnote/diaryreg";
        }

        Member member = customUserDetails.getLoggedMember();
        Integer memberID = member.getId();
        Pet pet = petnoteService.findFirstPet(memberID);

        diaryDto.setMember(member);
        diaryDto.setPet(pet);

        model.addAttribute("petID", petID);

        petnoteService.diarySave(diaryDto);
        System.out.println("다이어리컨트롤러 --> 서비스");
        return "redirect:/petnote/diaryreg/" + petID;
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
