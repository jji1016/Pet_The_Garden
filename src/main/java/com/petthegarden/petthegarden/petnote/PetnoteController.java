package com.petthegarden.petthegarden.petnote;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.petnote.dto.DiaryDto;
import com.petthegarden.petthegarden.petnote.dto.InfoDto;
import com.petthegarden.petthegarden.petnote.dto.PetDto;
import jakarta.transaction.Transactional;
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

    @Transactional
    @GetMapping("/list")
    public String list(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                       Model model) {
        if (customUserDetails.getLoggedMember().getPetList().isEmpty()) {
            model.addAttribute("petError", "펫을 등록해주세요.");
            return "petnote/list";
        }

        List<PetDto> petDtoList = petnoteService.findAllPetDto();
        model.addAttribute("petDtoList", petDtoList);
//        model.addAttribute("petError", "");
        return "petnote/list";
    }


    @GetMapping("/profile/{petID}")
    public String profile(@PathVariable int petID,
                          Model model,
                          @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (customUserDetails == null) {
            model.addAttribute("loginError", "로그인 후 이용 가능합니다.");
            return "member/login";
        }

        String loggedUserID = customUserDetails.getLoggedMember().getUserID();
        Integer memberID = customUserDetails.getLoggedMember().getId();

        List<Pet> petList = petnoteService.getPetList(memberID);
        PetDto petDto = petnoteService.getPetDtoByPetID(petID);
        String userID = petnoteService.getUserIDByPetID(petID);
        InfoDto infoDto = petnoteService.findByUserID(loggedUserID, userID);
        System.out.println("PetnoteController 펫디티오 " + petDto);

        model.addAttribute("userID", userID);
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
        System.out.println("프로필 펫디티오" + selectedPetDto);

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

        String userID = petnoteService.getUserIDByPetID(petID);

        model.addAttribute("userID", userID);
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
        Pet pet = petnoteService.findById(petID);

        diaryDto.setMember(member);
        diaryDto.setPet(pet);
        model.addAttribute("petID", petID);

        petnoteService.diarySave(diaryDto);
        System.out.println("다이어리컨트롤러 --> 서비스");
        return "redirect:/petnote/diary/" + petID;
    }


    @PostMapping("/diaryreg/upload-image")
    @ResponseBody
    public Map<String, String> uploadImage(@RequestParam("upload") MultipartFile uploadFile) throws IOException {
        String imageUrl = petnoteService.uploadImg(uploadFile);
        Map<String, String> response = new HashMap<>();
        response.put("url", imageUrl);
        return response;
    }

    @GetMapping("/diary/{petID}")
    public String diary(@PathVariable Integer petID, Model model) {
        List<DiaryDto> diaryDtoList = petnoteService.findAllDiaryDto(petID);

        Integer memberID = petnoteService.findMemberIDByPetID(petID);
        List<Pet> petList = petnoteService.getPetList(memberID);

        model.addAttribute("petList", petList);
        model.addAttribute("diaryDtoList", diaryDtoList);
        model.addAttribute("petID", petID);
        return "petnote/diary";
    }

    @GetMapping("/diaryview/{diaryID}")
    public String diaryview(@PathVariable Integer diaryID, Model model) {
        DiaryDto diaryDto = petnoteService.getDiaryDto(diaryID);
        model.addAttribute("diaryDto", diaryDto);
        model.addAttribute("diaryID", diaryID);
        return "petnote/diaryview";
    }
}
