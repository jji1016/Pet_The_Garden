package com.petthegarden.petthegarden.showoff.service;

import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.entity.ShowOff;
import com.petthegarden.petthegarden.showoff.dto.ShowRegDto;
import com.petthegarden.petthegarden.showoff.repository.ShowRegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Service
@Transactional
public class ShowRegService {

    private final ShowRegRepository showRegRepository;

    @Value("${file.path}")
    private String uploadDir;

    @Autowired
    public ShowRegService(ShowRegRepository showRegRepository) {
        this.showRegRepository = showRegRepository;
    }

    /**
     * 장기자랑 등록
     */
    public ShowOff registerShowOff(ShowRegDto dto, Member member, Pet pet) {
        ShowOff showOff = ShowOff.builder()
                .subject(dto.getSubject())
                .content(dto.getContent())
                .regDate(LocalDateTime.now())
                .member(member)
                .pet(pet)
                .showOffLike(0)
                .build();
        return showRegRepository.save(showOff);
    }

    /**
     * 모든 장기자랑 조회 (최신순)
     */
    @Transactional(readOnly = true)
    public List<ShowOff> getAllShowOffs() {
        return showRegRepository.findAll();
    }

    /**
     * 장기자랑 상세 조회
     */
    @Transactional(readOnly = true)
    public Optional<ShowOff> getShowOffById(Integer id) {
        return showRegRepository.findByIdWithComments(id);
    }

    /**
     * 특정 회원의 장기자랑 목록 조회
     */
    @Transactional(readOnly = true)
    public List<ShowOff> getShowOffsByMemberId(Integer memberId) {
        return showRegRepository.findByMemberIdOrderByRegDateDesc(memberId);
    }

    /**
     * 특정 반려동물의 장기자랑 목록 조회
     */
    @Transactional(readOnly = true)
    public List<ShowOff> getShowOffsByPetId(Integer petId) {
        return showRegRepository.findByPetIdOrderByRegDateDesc(petId);
    }

    /**
     * 제목으로 장기자랑 검색
     */
    @Transactional(readOnly = true)
    public List<ShowOff> searchBySubject(String keyword) {
        return showRegRepository.findBySubjectContainingIgnoreCaseOrderByRegDateDesc(keyword);
    }

    /**
     * 인기 장기자랑 조회 (Top 10)
     */
    @Transactional(readOnly = true)
    public List<ShowOff> getPopularShowOffs() {
        return showRegRepository.findTop10ByOrderByShowOffLikeDescRegDateDesc();
    }

    /**
     * 최신 장기자랑 조회 (Top 10)
     */
    @Transactional(readOnly = true)
    public List<ShowOff> getLatestShowOffs() {
        return showRegRepository.findTop10ByOrderByRegDateDesc();
    }

    /**
     * 장기자랑 수정
     * 엔티티에 setter가 없으므로 새 객체를 생성해서 저장
     */
    public ShowOff updateShowOff(Integer id, ShowRegDto dto) {
        Optional<ShowOff> optionalShowOff = showRegRepository.findById(id);
        if (optionalShowOff.isPresent()) {
            ShowOff oldShowOff = optionalShowOff.get();
            ShowOff updatedShowOff = ShowOff.builder()
                    .Id(oldShowOff.getId())
                    .subject(dto.getSubject())
                    .content(dto.getContent())
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

    /**
     * 장기자랑 삭제
     */
    public void deleteShowOff(Integer id) {
        if (showRegRepository.existsById(id)) {
            showRegRepository.deleteById(id);
        } else {
            throw new RuntimeException("장기자랑을 찾을 수 없습니다. ID: " + id);
        }
    }

    /**
     * 좋아요 증가
     * 엔티티에 setter가 없으므로 새 객체를 생성해서 저장
     */
    public ShowOff increaseLike(Integer id) {
        Optional<ShowOff> optionalShowOff = showRegRepository.findById(id);
        if (optionalShowOff.isPresent()) {
            ShowOff oldShowOff = optionalShowOff.get();
            ShowOff updatedShowOff = ShowOff.builder()
                    .Id(oldShowOff.getId())
                    .subject(oldShowOff.getSubject())
                    .content(oldShowOff.getContent())
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

    /**
     * 이미지 파일 업로드
     */
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
        return "/uploads/images/" + uniqueFilename;
    }

    /**
     * 업로드된 파일 삭제
     */
    public void deleteFile(String filename) {
        try {
            Path filePath = Paths.get(uploadDir, filename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("파일 삭제 실패: " + filename, e);
        }
    }
}
