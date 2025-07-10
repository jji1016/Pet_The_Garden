package com.petthegarden.petthegarden.petnote.dao;

import com.petthegarden.petthegarden.entity.Diary;
import com.petthegarden.petthegarden.petnote.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class DiaryDao {
    private final DiaryRepository diaryRepository;

    public void save(Diary diary) {
        diaryRepository.save(diary);
    }
}
