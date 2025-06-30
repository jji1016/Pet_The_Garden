package com.petthegarden.petthegarden.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PetGender {
    MALE("수컷"),
    FEMALE("암컷"),
    NEUTRAL("중성화"),
    UNKNOWN("알 수 없음");
    private final String label;
}
