package com.petthegarden.petthegarden.petnote;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.petnote.service.PetnoteService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


//필요한것같아서 썻지만 나중에 이상해지면 그냥 삭제하기 ---------------------------------------------------

@ControllerAdvice
public class GlobalModelAttribute {

    private final PetnoteService petnoteService;

    public GlobalModelAttribute(PetnoteService petnoteService) {
        this.petnoteService = petnoteService;
    }

    @ModelAttribute("petID")
    public Integer injectPetID(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                               Model model) {
        // 이미 컨트롤러에서 설정한 petID가 있으면 자동 주입하지 않음
        if (model.containsAttribute("petID")) {
            return (Integer) model.asMap().get("petID");
        }

        // 로그인 사용자의 첫 번째 petID 조회
        if (customUserDetails != null && customUserDetails.getLoggedMember() != null) {
            Integer memberId = customUserDetails.getLoggedMember().getId();

            Pet firstPet = petnoteService.findFirstPet(memberId);
            Integer petID = (firstPet != null) ? firstPet.getId() : null;

            return petID;
        }

        return null;
    }
}

