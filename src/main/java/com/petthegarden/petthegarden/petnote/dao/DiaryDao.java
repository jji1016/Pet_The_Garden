package com.petthegarden.petthegarden.petnote.dao;

import com.petthegarden.petthegarden.entity.Diary;
import com.petthegarden.petthegarden.petnote.dto.DiaryDto;
import com.petthegarden.petthegarden.petnote.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class DiaryDao {
    private final DiaryRepository diaryRepository;

    public void save(Diary diary) {
        diaryRepository.save(diary);
    }

    public List<Diary> findAllDiaryDto(Integer petID) {
        return diaryRepository.findByPet_IdOrderByRegDateDesc(petID);
    }

    public Diary findDiaryDto(Integer petID) {
        return diaryRepository.findById(petID).get();
    }
}
