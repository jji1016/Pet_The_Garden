package com.petthegarden.petthegarden.petnote;

import com.petthegarden.petthegarden.entity.Diary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PetnoteDao {
    private final PetnoteRepository petnoteRepository;

    public void save(Diary diary) {
        petnoteRepository.save(diary);
    }
}
