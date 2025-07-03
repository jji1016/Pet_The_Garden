package com.petthegarden.petthegarden.mypage.service;

import com.petthegarden.petthegarden.constant.Gender;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.member.MemberRepository;
import com.petthegarden.petthegarden.mypage.dao.MypageDao;
import com.petthegarden.petthegarden.mypage.dto.MemberDto;
import com.petthegarden.petthegarden.mypage.dto.PetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MypageService {
    @Value("${file.path}pet/")
    String petPath;
    private final MypageDao mypageDao;
    private final MemberRepository memberRepository;

    public MemberDto findByUserID(String userID) {
        Optional<Member> optionalMember = mypageDao.findByUserID(userID);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return member.toMemberDto();
        } else {
            return null;
        }
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
}