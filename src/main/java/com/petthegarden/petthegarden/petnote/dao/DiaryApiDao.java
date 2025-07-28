package com.petthegarden.petthegarden.petnote.dao;

import com.petthegarden.petthegarden.entity.Diary;
import com.petthegarden.petthegarden.petnote.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DiaryApiDao {
    private final DiaryRepository diaryRepository;


    public Page<Diary> findByPet_Id(Integer petID, Pageable pageable) {
        return diaryRepository.findByPet_Id(petID, pageable);
    }
}
