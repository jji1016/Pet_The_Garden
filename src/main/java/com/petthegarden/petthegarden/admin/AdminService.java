package com.petthegarden.petthegarden.admin;

import com.petthegarden.petthegarden.admin.dto.AdminMemberDto;
import com.petthegarden.petthegarden.admin.dto.AdminShowOffDto;
import com.petthegarden.petthegarden.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final AdminDao adminDao;

    public int totalMember() {
        return adminDao.totalMember();
    }

    public int totalPet() {
        return adminDao.totalPet();
    }

    public int newMember() {
        return adminDao.newMember();
    }

    public int recentPet() {
        return adminDao.recentPet();
    }

    public int recentStray() {
        return adminDao.recentStray();
    }

    public int recentShowOff() {
        return adminDao.recentShowOff();
    }

    public int recentBoard() {
        return adminDao.recentBoard();
    }

    public List<AdminShowOffDto> getShowOffList() {
        return adminDao.getShowOffList();
    }

    public Page<AdminMemberDto> getMemberList(String startDate, String endDate, String key, String search, Pageable pageable) {
        return adminDao.getMemberList(startDate,endDate,key,search,pageable);
    }
}
