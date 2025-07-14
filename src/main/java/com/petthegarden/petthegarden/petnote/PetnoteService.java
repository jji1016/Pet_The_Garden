package com.petthegarden.petthegarden.petnote;

import com.petthegarden.petthegarden.entity.Diary;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.member.MemberDao;
import com.petthegarden.petthegarden.petnote.dao.PetDao;
import com.petthegarden.petthegarden.petnote.dao.DiaryDao;
import com.petthegarden.petthegarden.petnote.dto.DiaryDto;
import com.petthegarden.petthegarden.petnote.dto.InfoDto;
import com.petthegarden.petthegarden.petnote.dto.PetDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
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
        LocalDateTime now = LocalDateTime.now();
        String dateFolder = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Path uploadFolder = Paths.get(upload, dateFolder);

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


    public InfoDto findByUserID(String loggedUserID, String userID) {
        Optional<Member> optionalMember = memberDao.findByUserID(userID);
        if(optionalMember.isPresent()){
            Member member = optionalMember.get();
            return InfoDto.builder()
                    .pageOwner(loggedUserID.equals(userID))
                    .member(member)
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
}
