package com.petthegarden.petthegarden.follow;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping("/follow/{toPetID}")
    public Map<String,Object> follow(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                        @PathVariable String toPetID) {
        String fromMemberID = customUserDetails.getLoggedMember().getUserID();
        int result =  followService.follow(fromMemberID, toPetID);
        Map<String,Object> resultMap = new HashMap<>();
        if(result > 0) {
            resultMap.put("isSubscribe", true);
        } else {
            resultMap.put("isSubscribe", false);
        }
        return resultMap;
    }

//    @DeleteMapping("/follow/{toPetID}")
//    public Map<String,Object> unFollow(@AuthenticationPrincipal CustomUserDetails customUserDetails,
//                                          @PathVariable String toPetID) {
//        String fromMemberID = customUserDetails.getLoggedMember().getUserID();
//        int result =  followService.unfollow(fromMemberID, toPetID);
//        Map<String,Object> resultMap = new HashMap<>();
//        if(result > 0) {
//            resultMap.put("isSubscribeDelete", true);
//        } else {
//            resultMap.put("isSubscribeDelete", false);
//        }
//        return resultMap;
//    }
}
