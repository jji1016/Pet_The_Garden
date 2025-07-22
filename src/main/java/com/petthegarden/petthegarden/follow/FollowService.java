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
        public int follow(String fromMemberID, String toMemberID) {
            return followDao.follow(fromMemberID, toMemberID);
        }


//        public List<FollowDto> getFollowList(String currentUserID, String targetUserID) {
//            return followDao.getFollowList(currentUserID, targetUserID);
//        }
//
//        @Transactional
//        public int unfollow(String fromMemberID, String toMemberID) {
//            return followDao.unfollow(fromMemberID, toMemberID);
//        }
    }

