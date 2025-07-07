package com.petthegarden.petthegarden.stray;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class StrayDao {
    private final StrayRepository strayRepository;

    public void deleteAllInBatch() {
        strayRepository.deleteAllInBatch();
    }
}
