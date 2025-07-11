package com.petthegarden.petthegarden.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AdminDao {
    private final AdminRepository adminRepository;

    public int totalMember() {
        return adminRepository.totalMember();
    }

    public int totalPet() {
        return adminRepository.totalPet();
    }

    public int newMember() {
        return adminRepository.newMember();
    }

    public int recentPet() {
        return adminRepository.recentPet();
    }

    public int recentStray() {
        return adminRepository.recentStray();
    }

    public int recentShowOff() {
        return adminRepository.recentShowOff();
    }

    public int recentBoard() {
        return adminRepository.recentBoard();
    }
}
