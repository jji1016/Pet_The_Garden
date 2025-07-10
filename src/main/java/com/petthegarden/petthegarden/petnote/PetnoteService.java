package com.petthegarden.petthegarden.petnote;

import com.petthegarden.petthegarden.entity.Diary;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.petnote.dao.PetDao;
import com.petthegarden.petthegarden.petnote.dao.DiaryDao;
import com.petthegarden.petthegarden.petnote.dto.DiaryDto;
import com.petthegarden.petthegarden.petnote.dto.PetDto;
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
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PetnoteService {
    @Value("${file.path}diary/")
    private String upload;
    private final DiaryDao diaryDao;
    private final PetDao petDao;


    public List<Pet> getPetList(Integer memberID) {
        List<Pet> petList = petDao.getPetList(memberID);
        System.out.println("petnoteService 펫리스트" + petList.size());
        return petList;
    }

    public PetDto getPetDto(Integer memberID) {
        Pet pet = petDao.findFirstPet(memberID);
        return PetDto.toPetDto(pet);
    }

    public void diarySave(DiaryDto diaryDto) {
        Diary diary = DiaryDto.toDiary(diaryDto);
        diaryDao.save(diary);
    }

    public Pet findFirstPet(Integer memberID) {
        Pet pet = petDao.findFirstPet(memberID);
        return pet;
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

        return "/PTGupload/diary/" + fileName;
    }
}
