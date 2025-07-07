package com.petthegarden.petthegarden.petnote.dao;

import com.petthegarden.petthegarden.entity.Diary;
import com.petthegarden.petthegarden.petnote.repository.PetnoteRepository;
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
