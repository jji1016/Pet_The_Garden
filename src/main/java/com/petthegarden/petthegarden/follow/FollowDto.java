package com.petthegarden.petthegarden.follow;

import lombok.*;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class FollowDto {
        private String userID;
        private String userName;
        private boolean isMe;        // 본인인지
        private boolean isSubscribed; // 내가 구독하고 있는지
    }
