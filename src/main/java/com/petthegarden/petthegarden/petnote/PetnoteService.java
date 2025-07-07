package com.petthegarden.petthegarden.petnote;

import com.petthegarden.petthegarden.entity.Diary;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PetnoteService {
    private final PetnoteDao petnoteDao;

    public void save(DiaryDto diaryDto) {
        Diary savedDiary = diaryDto.toDiary();
        petnoteDao.save(savedDiary);
    }
}
