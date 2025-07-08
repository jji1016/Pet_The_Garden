package com.petthegarden.petthegarden.mypage.service;

import com.petthegarden.petthegarden.constant.Gender;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.member.MemberRepository;
import com.petthegarden.petthegarden.mypage.dao.MypageDao;
import com.petthegarden.petthegarden.mypage.dto.MemberDto;
import com.petthegarden.petthegarden.mypage.dto.PetDto;
import com.petthegarden.petthegarden.mypage.repository.MypagePetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MypageService {
    @Value("${file.path}pet/")
    String petPath;
    private final MypageDao mypageDao;
    private final MemberRepository memberRepository;
    private final MypagePetRepository mypagePetRepository;

    public MemberDto findByUserID(String userID) {
        Optional<Member> optionalMember = mypageDao.findByUserID(userID);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return member.toMemberDto();
        } else {
            return null;
        }
    }

    public Member findMemberByUserID(String userID) {
        return mypageDao.findByUserID(userID)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));
    }

    public boolean deletePet(Integer petId, Member member) {
        Optional<Pet> petOpt = mypagePetRepository.findById(petId);

        if (petOpt.isEmpty()) {
            return false;
        }

        Pet pet = petOpt.get();

        // 요청한 회원의 펫인지 체크
        if (!pet.getMember().getId().equals(member.getId())) {
            return false; // 소유자가 아니므로 삭제 불가
        }
        mypagePetRepository.delete(pet);
        return true;
    }

    public void updateInfo(MemberDto memberDto) {
        Member member = mypageDao.findById(memberDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        member.updateInfo(
                memberDto.getUserPW(),
                memberDto.getUserEmail(),
                memberDto.getTel(),
                memberDto.getZipcode(),
                memberDto.getAddress01(),
                memberDto.getAddress02()
        );
    }
    public void petreg(PetDto petDto, Member member, MultipartFile file) {
        String fileName = null;

        if (file != null && !file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String baseName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
                String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                fileName = baseName + "_" + timestamp + extension;

                File saveDir = new File(petPath);
                if (!saveDir.exists()) {
                    saveDir.mkdirs();
                }

                file.transferTo(new File(petPath + fileName));
            } catch (Exception e) {
                log.error("반려동물 이미지 업로드 실패", e);
                throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다.");
            }
        }

        Pet pet = petDto.toEntity(member);
        pet.setProfileImg(fileName);
        mypageDao.savePet(pet);
    }


    public Member findById(int id) {
        return memberRepository.findById(id).orElse(null);
    }

    public Map<String, Object> uploadImage(MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        if (file == null || file.isEmpty()) {
            response.put("uploaded", 0);
            response.put("error", Map.of("message", "파일이 없습니다."));
            return response;
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String baseName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String fileName = baseName + "_" + timestamp + extension;

            File saveDir = new File(petPath); // 폴더에 저장
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            File savedFile = new File(saveDir, fileName);
            file.transferTo(savedFile);

            response.put("uploaded", 1);
            response.put("fileName", fileName);
            response.put("url", "/PTGUpload/pet/" + fileName); // 임시 경로

        } catch (Exception e) {
            response.put("uploaded", 0);
            response.put("error", Map.of("message", "업로드 실패: " + e.getMessage()));
        }

        return response;
    }

    public List<PetDto> findPetsByMemberId(Integer memberId) {
        List<Pet> petList = mypageDao.findByMemberId(memberId); // 엔티티 그대로
        List<PetDto> petDtoList = new ArrayList<>();

        for (Pet pet : petList) {
            petDtoList.add(PetDto.fromEntity(pet)); // 엔티티 -> DTO로 변환
        }

        return petDtoList;
    }
}