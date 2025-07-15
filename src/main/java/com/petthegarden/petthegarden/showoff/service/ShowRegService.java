package com.petthegarden.petthegarden.showoff.service;

import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.entity.ShowOff;
import com.petthegarden.petthegarden.showoff.dto.ShowPetDto;
import com.petthegarden.petthegarden.showoff.dto.ShowRegDto;
import com.petthegarden.petthegarden.showoff.repository.ShowRegRepository;
import com.petthegarden.petthegarden.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class ShowRegService {

    private final ShowRegRepository showRegRepository;
    private final MemberRepository memberRepository;

    // yml 사용 안함, 하드코딩 경로
    private String uploadDir = "C:/PTGUpload/f1";

    @Autowired
    public ShowRegService(ShowRegRepository showRegRepository, MemberRepository memberRepository) {
        this.showRegRepository = showRegRepository;
        this.memberRepository = memberRepository;
    }

    // 장기자랑 등록
    public ShowOff registerShowOff(ShowRegDto dto, Member member) {
        Integer petID = dto.getPetId();
        Pet petEntityList = showRegRepository.getPetEntityList(petID);
        log.info("petEntityList={}", petEntityList);

        ShowOff showOff = ShowOff.builder()
                .pet(petEntityList)
                .subject(dto.getSubject())
                .content(dto.getContent())
                .image(dto.getImage())
                .regDate(LocalDateTime.now())
                .member(member)
                .showOffLike(0)
                .build();

        System.out.println(showOff);

        return showRegRepository.save(showOff);
    }

    // 장기자랑 수정
    public ShowOff updateShowOff(Integer id, ShowRegDto dto) {
        Optional<ShowOff> optionalShowOff = showRegRepository.findById(id);
        if (optionalShowOff.isPresent()) {
            ShowOff oldShowOff = optionalShowOff.get();
            ShowOff updatedShowOff = ShowOff.builder()
                    .Id(oldShowOff.getId())
                    .subject(dto.getSubject())
                    .content(dto.getContent())
                    .image(dto.getImage())
                    .regDate(oldShowOff.getRegDate())
                    .modifyDate(LocalDateTime.now())
                    .member(oldShowOff.getMember())
                    .pet(oldShowOff.getPet())
                    .showOffLike(oldShowOff.getShowOffLike())
                    .build();

            return showRegRepository.save(updatedShowOff);
        } else {
            throw new RuntimeException("장기자랑을 찾을 수 없습니다. ID: " + id);
        }
    }

    @Transactional(readOnly = true)
    public List<ShowOff> getAllShowOffs() {
        return showRegRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ShowOff> getShowOffById(Integer id) {
        return showRegRepository.findByIdWithComments(id);
    }

    @Transactional(readOnly = true)
    public List<ShowOff> getShowOffsByMemberId(Integer memberId) {
        return showRegRepository.findByMemberIdOrderByRegDateDesc(memberId);
    }

    @Transactional(readOnly = true)
    public List<ShowOff> searchBySubject(String keyword) {
        return showRegRepository.findBySubjectContainingIgnoreCaseOrderByRegDateDesc(keyword);
    }

    @Transactional(readOnly = true)
    public List<ShowOff> getPopularShowOffs() {
        return showRegRepository.findTop10ByOrderByShowOffLikeDescRegDateDesc();
    }

    @Transactional(readOnly = true)
    public List<ShowOff> getLatestShowOffs() {
        return showRegRepository.findTop10ByOrderByRegDateDesc();
    }

    @Transactional(readOnly = true)
    public Member getMemberById(Integer memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }

    public void deleteShowOff(Integer id) {
        if (showRegRepository.existsById(id)) {
            showRegRepository.deleteById(id);
        } else {
            throw new RuntimeException("장기자랑을 찾을 수 없습니다. ID: " + id);
        }
    }

    public ShowOff increaseLike(Integer id) {
        Optional<ShowOff> optionalShowOff = showRegRepository.findById(id);
        if (optionalShowOff.isPresent()) {
            ShowOff oldShowOff = optionalShowOff.get();
            ShowOff updatedShowOff = ShowOff.builder()
                    .Id(oldShowOff.getId())
                    .subject(oldShowOff.getSubject())
                    .content(oldShowOff.getContent())
                    .image(oldShowOff.getImage())
                    .regDate(oldShowOff.getRegDate())
                    .modifyDate(oldShowOff.getModifyDate())
                    .member(oldShowOff.getMember())
                    .pet(oldShowOff.getPet())
                    .showOffLike(oldShowOff.getShowOffLike() + 1)
                    .build();

            return showRegRepository.save(updatedShowOff);
        } else {
            throw new RuntimeException("장기자랑을 찾을 수 없습니다. ID: " + id);
        }
    }

    // 이미지 파일 업로드
    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("업로드할 파일이 없습니다.");
        }

        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + extension;

        Path filePath = Paths.get(uploadDir, uniqueFilename);
        Files.copy(file.getInputStream(), filePath);

        return "/PTGUpload/images/" + uniqueFilename;
    }

    // 업로드된 파일 삭제
    public void deleteFile(String filename) {
        try {
            Path filePath = Paths.get(uploadDir, filename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("파일 삭제 실패: " + filename, e);
        }
    }

    public List<ShowPetDto> getPetList(Integer memberID) {
        return showRegRepository.getPetList(memberID);
    }
}
