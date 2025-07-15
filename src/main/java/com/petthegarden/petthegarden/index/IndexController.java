package com.petthegarden.petthegarden.index;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.petnote.PetnoteService;
import com.petthegarden.petthegarden.petnote.dto.PetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
@RequiredArgsConstructor
public class IndexController {
    private final PetnoteService petnoteService;

    @GetMapping("/index")
    public String index(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                        Model model) {

        if (customUserDetails != null) {
            Integer memberID = customUserDetails.getLoggedMember().getId();
            Pet pet = petnoteService.findFirstPet(memberID);

            if (pet != null) {
                PetDto firstPetDto = petnoteService.getPetDto(memberID);
                model.addAttribute("petID", firstPetDto.getPetID());
            }
        }
        return "index/index";
    }
}

