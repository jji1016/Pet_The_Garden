package com.petthegarden.petthegarden.follow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
        private final FollowDao followDao;

        @Transactional
        public int follow(String fromMemberID, String toPetID) {
            return followDao.follow(fromMemberID, toPetID);
        }


//        public List<FollowDto> getFollowList(String currentUserID, String targetUserID) {
//            return followDao.getFollowList(currentUserID, targetUserID);
//        }

        @Transactional
        public int unfollow(String fromMemberID, String toPetID) {
            return followDao.unfollow(fromMemberID, toPetID);
        }
    }

