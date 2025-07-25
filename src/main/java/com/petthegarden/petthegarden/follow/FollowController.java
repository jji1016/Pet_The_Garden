package com.petthegarden.petthegarden.follow;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.communal.service.CustomUserDetailsService;
import com.petthegarden.petthegarden.petnote.PetnoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class FollowController {
    private final FollowService followService;
    private final PetnoteService petnoteService;

    @PostMapping("/follow/{toPetID}")
    public Map<String,Object> follow(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                     @PathVariable Integer toPetID) {
        Integer fromMemberID = customUserDetails.getLoggedMember().getId();
        int result =  followService.follow(fromMemberID, toPetID);
        System.out.println("result === " + result);
        Map<String,Object> resultMap = new HashMap<>();
        if(result > 0) {
            resultMap.put("isFollow", true);
        } else {
            resultMap.put("isFollow", false);
        }
        return resultMap;
    }

    @DeleteMapping("/follow/{toPetID}")
    public Map<String,Object> unFollow(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                          @PathVariable Integer toPetID) {
        int fromMemberID = customUserDetails.getLoggedMember().getId();

        int result =  followService.unfollow(fromMemberID, toPetID);
        System.out.println("언팔result!!!!!!! === " + result);
        Map<String,Object> resultMap = new HashMap<>();
        if(result > 0) {
            resultMap.put("isFollowDelete", true);
        } else {
            resultMap.put("isFollowDelete", false);
        }
        return resultMap;
    }
}
