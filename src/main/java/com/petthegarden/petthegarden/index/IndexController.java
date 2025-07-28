package com.petthegarden.petthegarden.index;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.entity.Pet;

import com.petthegarden.petthegarden.index.dto.RecentBoardDto;
import com.petthegarden.petthegarden.index.dto.Top3PetDto;
import com.petthegarden.petthegarden.index.dto.WeeklyTop3Dto;
import com.petthegarden.petthegarden.petnote.service.PetnoteService;
import com.petthegarden.petthegarden.petnote.dto.PetDto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/index")
@RequiredArgsConstructor
public class IndexController {
    private final PetnoteService petnoteService;
    private final MainService mainService;

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

        List<WeeklyTop3Dto> bestShowOffList = mainService.getWeeklyTop3();
        model.addAttribute("bestShowOffList", bestShowOffList);
        System.out.println("이번주 베스트 3 === " + bestShowOffList);

        List<Top3PetDto> top3Pets = mainService.getTop3pet();
        model.addAttribute("top3Pets", top3Pets);

        List<RecentBoardDto> recentBoardDto = mainService.findRecentBoards();
        model.addAttribute("recentBoardDto", recentBoardDto);


        return "index/index";
    }



}

