package com.petthegarden.petthegarden.petnote.service;

import com.petthegarden.petthegarden.entity.Diary;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.member.MemberDao;
import com.petthegarden.petthegarden.petnote.dao.PetDao;
import com.petthegarden.petthegarden.petnote.dao.DiaryDao;
import com.petthegarden.petthegarden.petnote.dto.DiaryDto;
import com.petthegarden.petthegarden.petnote.dto.PetDto;
import com.petthegarden.petthegarden.petnote.dto.PetInfo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PetnoteService {
    @Value("${file.path}diary/")
    private String upload;
    private final DiaryDao diaryDao;
    private final PetDao petDao;
    private final MemberDao memberDao;


    public List<Pet> getPetList(Integer memberID) {
        List<Pet> petList = petDao.getPetList(memberID);
        System.out.println("petnoteService 펫리스트" + petList.size());
        return petList;
    }


    public PetDto getPetDto(Integer memberID) {
        Pet pet = petDao.findFirstPet(memberID);
        return PetDto.toPetDto(pet);
    }


    public Pet findFirstPet(Integer memberID) {
        return petDao.findFirstPet(memberID);
    }


    public void diarySave(DiaryDto diaryDto) {

        System.out.println("다이어리세이브 들어옴");
        Path uploadFolder = Paths.get(upload);

        try {
            Files.createDirectories(uploadFolder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (diaryDto.getDiaryImg() != null && !diaryDto.getDiaryImg().isEmpty()) {
            String rawImage = diaryDto.getDiaryImg().getOriginalFilename();
            String extension = rawImage.substring(rawImage.lastIndexOf(".") + 1);
            String fileName = rawImage.substring(0, rawImage.lastIndexOf("."));
            String diaryImg= fileName + "." + extension;
            Path saveProfile = uploadFolder.resolve(diaryImg);

            try {
                diaryDto.getDiaryImg().transferTo(saveProfile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            diaryDto.setImage(diaryImg);
        }

        System.out.println("다이어리이미지 셋");
        Diary diary = DiaryDto.toDiary(diaryDto);
        diaryDao.save(diary);
    }


    public String uploadImg(MultipartFile uploadFile)  throws IOException {

        Path uploadPath = Paths.get(upload);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = uploadFile.getOriginalFilename();
        String fileName = System.currentTimeMillis() + "_" + originalFilename;
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(uploadFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/PTGUpload/diary/" + fileName;
    }


    public PetDto getPetDtoByPetID(Integer petID) {
        Optional<Pet> pet = petDao.findPetByPetID(petID);

        if (pet.isPresent()) {
            return PetDto.toPetDto(pet.get());
        } else {
            throw new EntityNotFoundException("해당 petID의 펫을 찾을 수 없습니다: " + petID);
        }
    }


    public PetInfo findByUserID(Integer petID) {
        Optional<Pet> optionalPet = petDao.findById(petID);
        if(optionalPet.isPresent()){
            Pet pet = optionalPet.get();
            return PetInfo.builder()
                    .diaryCount(pet.getDiaryList().size())
                    .showOffCount(pet.getShowOffList().size())
                    .build();
        }
        return null;
    }


    public String getUserIDByPetID(int petID) {
        return petDao.getUserIDByPetID(petID);
    }

    public List<PetDto> findAllPetDto() {
        return petDao.findAllPetDto();
    }

    public List<DiaryDto> findAllDiaryDto(Integer petID) {
        log.info("petID: " + petID);
        List<Diary> diaryList = diaryDao.findAllDiaryDto(petID);
        log.info("diaryList=={}", diaryList);
        return DiaryDto.toDiaryDtoList(diaryList);
    }

    public DiaryDto getDiaryDto(Integer petID) {
         Diary diary = diaryDao.findDiaryDto(petID);
        return DiaryDto.toDiaryDto(diary);
    }

    public Integer findMemberIDByPetID(Integer petID) {
        return petDao.findMemberIDByPetID(petID);
    }

    public Pet findById(Integer petID) {
        Optional<Pet> pet = petDao.findById(petID);
        if (pet.isPresent()) {
            log.info("안녕하세요 ");
            return pet.get();
        }
        return null;
    }

    public Page<PetDto> getAllPetDtoPaged(Pageable pageable) {
        return petDao.findAllPetDtoPaged(pageable);
    }

    public Integer getPetIDByDiaryID(Integer diaryID) {
        return petDao.getPetIDByDiaryID(diaryID);
    }
}
