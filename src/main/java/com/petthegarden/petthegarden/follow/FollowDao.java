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

    public int follow(Integer fromMemberID, Integer toPetID) {
        return followRepository.follow(fromMemberID, toPetID);
    }


    public int unfollow(Integer fromMemberID, Integer toPetID) {
        return followRepository.unfollow(fromMemberID, toPetID);
    }

    public int getFollowers(Integer toPetID) {
        return followRepository.getFollowers(toPetID);
    }

    public boolean isFollowing (Integer myMemberId, Integer targetPetId) {
        return followRepository.existsByMember_IdAndPet_Id(myMemberId, targetPetId);
    }
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

