package com.petthegarden.petthegarden.admin;

import com.petthegarden.petthegarden.admin.dto.AdminMemberDto;
import com.petthegarden.petthegarden.admin.dto.AdminPetDto;
import com.petthegarden.petthegarden.admin.dto.AdminReportDto;
import com.petthegarden.petthegarden.admin.dto.AdminShowOffDto;
import com.petthegarden.petthegarden.constant.ReportType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    public List<AdminShowOffDto> getShowOffList() {
        return adminRepository.getShowOffList(PageRequest.of(0, 5));
    }

    public Page<AdminMemberDto> getMemberList(String startDate, String endDate, String key, String search, Pageable pageable) {
        return adminRepository.getMemberList(startDate,endDate,key,search,pageable);
    }

    public Page<AdminPetDto> getPetList(String startDate, String endDate, String key, String search, Pageable pageable) {
        return adminRepository.getPetList(startDate,endDate,key,search,pageable);
    }

    public Page<AdminReportDto> getAllReports(LocalDateTime startDate, LocalDateTime endDate, String key, String search, Pageable pageable) {
        return adminRepository.getAllReports(startDate, endDate, key, search, pageable);
    }

    public Page<AdminReportDto> getFilteredReports(ReportType type, LocalDateTime startDate, LocalDateTime endDate, String key, String search, Pageable pageable) {
        return adminRepository.getFilteredReports(type, startDate, endDate, key, search, pageable);
    }
}
