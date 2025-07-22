package com.petthegarden.petthegarden.admin;

import com.petthegarden.petthegarden.admin.dto.*;
import com.petthegarden.petthegarden.constant.ReportType;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public Page<AdminPetDto> getPetList(String startDate, String endDate, String key, String search, Pageable pageable) {
        return adminDao.getPetList(startDate,endDate,key,search,pageable);
    }

    public Page<AdminReportDto> getReportList(String typeStr, LocalDateTime startDate, LocalDateTime endDate, String key, String search, Pageable pageable) {
        if ("All".equalsIgnoreCase(typeStr)) {
            log.info("All로 들어감");
            return adminDao.getAllReports(startDate, endDate, key, search, pageable);
        }

        ReportType type = ReportType.valueOf(typeStr);
        log.info("타입 필터로 들어감");
        return adminDao.getFilteredReports(type, startDate, endDate, key, search, pageable);
    }

    public AdminMemberDetailDto findById(Integer memberID) {
        Optional<Member> optionalMember = adminDao.findById(memberID);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return AdminMemberDetailDto.builder()
                    .id(member.getId())
                    .userID(member.getUserID())
                    .userName(member.getUserName())
                    .image(member.getImage())
                    .email(member.getEmail())
                    .tel(member.getTel())
                    .regDate(member.getRegDate())
                    .zipcode(member.getZipcode())
                    .address01(member.getAddress01())
                    .address02(member.getAddress02())
                    .build();


        }
        return null;
    }

    public List<AdminPetDto> findByMember_Id(Integer memberID) {
        List<Pet> petList = adminDao.findByMember_Id(memberID);

        List<AdminPetDto> dtoList = petList.stream()
                .map(pet -> AdminPetDto.builder()
                        .petID(pet.getId())
                        .profileImg(pet.getProfileImg())
                        .petName(pet.getPetName())
                        .species(pet.getSpecies())
                        .follow(pet.getFollow())
                        .petGender(pet.getPetGender())
                        .regDate(pet.getRegDate())
                        .build()
                )
                .toList();

        return dtoList;
    }

    public List<AdminShowOffBoardDto> getShowOffBoardList(Integer memberID) {
        return adminDao.getShowOffBoardList(memberID);
    }

    public List<AdminShowOffCommentDto> getShowOffCommentList(Integer memberID) {
        return adminDao.getShowOffCommentList(memberID);
    }

    public List<AdminFreeBoardDto> getFreeBoardList(Integer memberID) {
        return adminDao.getFreeBoardList(memberID);
    }

    public List<AdminFreeCommentDto> getFreeCommentList(Integer memberID) {
        return adminDao.getFreeCommentList(memberID);
    }
}
