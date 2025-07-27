package com.petthegarden.petthegarden.admin;

import com.petthegarden.petthegarden.admin.dto.*;
import com.petthegarden.petthegarden.admin.repository.AdminPetRepository;
import com.petthegarden.petthegarden.admin.repository.AdminRepository;
import com.petthegarden.petthegarden.admin.repository.AdminRportRepository;
import com.petthegarden.petthegarden.constant.ReportType;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AdminDao {
    private final AdminRepository adminRepository;
    private final AdminPetRepository adminPetRepository;
    private final AdminRportRepository adminRportRepository;

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

    public Page<AdminReportDto> getReportBoardList(LocalDateTime startDate, LocalDateTime endDate, String key, String search, Pageable pageable) {
        return adminRepository.getReportBoardList(startDate,endDate,key,search,pageable);
    }
    public Page<AdminReportDto> getReportCommentList(LocalDateTime startDate, LocalDateTime endDate, String key, String search, Pageable pageable) {
        return adminRepository.getReportCommentList(startDate,endDate,key,search,pageable);
    }

    public Optional<Member> findById(Integer memberID) {
        return adminRepository.findById(memberID);
    }

    public List<Pet> findByMember_Id(Integer memberID) {
        List<Pet> petList = adminPetRepository.findByMember_Id(memberID);
        return petList;
    }

    public List<AdminShowOffBoardDto> getShowOffBoardList(Integer memberID) {
        return adminRepository.getShowOffBoardList(memberID);
    }

    public List<AdminShowOffCommentDto> getShowOffCommentList(Integer memberID) {
        return adminRepository.getShowOffCommentList(memberID);
    }

    public List<AdminFreeBoardDto> getFreeBoardList(Integer memberID) {
        return adminRepository.getFreeBoardList(memberID);
    }

    public List<AdminFreeCommentDto> getFreeCommentList(Integer memberID) {
        return adminRepository.getFreeCommentList(memberID);
    }

    public List<Object[]> getSpeciesChart() {
        return adminRepository.getSpeciesChart();
    }


    public void deleteById(Integer reportId) {
        adminRportRepository.deleteById(reportId);
    }
}
