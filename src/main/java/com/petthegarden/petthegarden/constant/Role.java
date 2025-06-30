package com.petthegarden.petthegarden.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    ROLE_USER("일반회원"),
    ROLE_ADMIN("관리자");
    private final String label;
}