package com.petthegarden.petthegarden.petnote.service;

import com.petthegarden.petthegarden.entity.Diary;
import com.petthegarden.petthegarden.petnote.dao.DiaryApiDao;
import com.petthegarden.petthegarden.petnote.dao.DiaryDao;
import com.petthegarden.petthegarden.petnote.dto.DiaryDto;
import com.petthegarden.petthegarden.petnote.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryApiService {
    private final DiaryApiDao diaryApiDao;

    public Page<DiaryDto> getDiaryListByPetId(Integer petID, Pageable pageable) {
        Page<Diary> diaryPage = diaryApiDao.findByPet_Id(petID, pageable);
        return diaryPage.map(DiaryDto::fromEntity); // or DiaryDto::toDiaryDto
    }
}

