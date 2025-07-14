package com.petthegarden.petthegarden.follow;

import com.petthegarden.petthegarden.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FollowDao {
    private final FollowRepository followRepository;

    public int follow(String fromMemberID, String toMemberID) {
        return followRepository.follow(fromMemberID, toMemberID);
    }

//    public int unfollow(String fromMemberID, String toMemberID) {
//        return followRepository.unfollow(fromMemberID, toMemberID);
//    }
//
//    public List<FollowDto> getSubscribeList(String currentUserID, String targetUserID) {
//        List<Member> subscribedMembers = followRepository.findfollowedUsers(targetUserID);
//
//        return subscribedMembers.stream().map(member -> FollowDto.builder()
//                .userID(member.getUserID())
//                .userName(member.getUserName())
//                .isMe(member.getUserID().equals(currentUserID))
//                .build()
//        ).toList();
//    }
}

