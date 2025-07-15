package com.petthegarden.petthegarden.petnote;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.petnote.dto.DiaryDto;
import com.petthegarden.petthegarden.petnote.dto.InfoDto;
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
    public String list(Model model) {
        List<PetDto> petDtoList = petnoteService.findAllPetDto();


        model.addAttribute("petDtoList", petDtoList);
        return "petnote/list";
    }


    @GetMapping("/profile/{petID}")
    public String profile(@PathVariable int petID,
                          Model model,
                          @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (customUserDetails == null) {
            model.addAttribute("loginError", "로그인 후 이용 가능합니다.");
            return "member/login"; // 로그인 안내 템플릿으로 이동
        }

        String loggedUserID = customUserDetails.getLoggedMember().getUserID();
        Integer memberID = customUserDetails.getLoggedMember().getId();

        List<Pet> petList = petnoteService.getPetList(memberID);
        PetDto petDto = petnoteService.getPetDtoByPetID(petID);
        String userID = petnoteService.getUserIDByPetID(petID);
        InfoDto infoDto = petnoteService.findByUserID(loggedUserID, userID);
        System.out.println("PetnoteController 펫디티오 " + petDto);

        model.addAttribute("petID", petID);
        model.addAttribute("petList", petList);
        model.addAttribute("infoDto", infoDto);
        model.addAttribute("petDto", petDto);

        return "petnote/profile";
    }


    @PostMapping("/profile/{petID}")
    public String changePetProfile(@RequestParam("petID") Integer petID,
                                   Model model,
                                   @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Integer memberID = customUserDetails.getLoggedMember().getId();
        List<Pet> petList = petnoteService.getPetList(memberID);
        PetDto selectedPetDto = petnoteService.getPetDtoByPetID(petID);

        model.addAttribute("memberID", memberID);
        model.addAttribute("petID", petID);
        model.addAttribute("petList", petList);
        model.addAttribute("firstPetDto", selectedPetDto);
        return "petnote/profile";
    }


    @GetMapping("/diaryreg/{petID}")
    public String diaryreg(@PathVariable Integer petID,
                           Model model,
                           @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String petName = petnoteService.getPetDtoByPetID(petID).getPetName();
        List<Pet> petList = petnoteService.getPetList(customUserDetails.getMemberId());

        model.addAttribute("diaryDto", new DiaryDto());
        model.addAttribute("petList", petList);
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
