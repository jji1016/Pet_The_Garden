package com.petthegarden.petthegarden.follow;

import com.petthegarden.petthegarden.entity.Follow;
import com.petthegarden.petthegarden.entity.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
        private final FollowDao followDao;

        @Transactional
        public int follow(Integer fromMemberID, Integer toPetID) {
            return followDao.follow(fromMemberID, toPetID);
        }


//        public List<FollowDto> getFollowList(String currentUserID, String targetUserID) {
//            return followDao.getFollowList(currentUserID, targetUserID);
//        }

        @Transactional
        public int unfollow(Integer fromMemberID, Integer toPetID) {
            return followDao.unfollow(fromMemberID, toPetID);
        }

        public int getFollowers(Integer toPetID) {
            return followDao.getFollowers(toPetID);
        }

    public boolean isFollowing(Integer myMemberId, Integer targetPetId) {
        return followDao.isFollowing(myMemberId, targetPetId);
    }
    public List<Pet> getFollowedPets(Integer memberId) {
        return followDao.getFollowedPets(memberId);
    }

}


