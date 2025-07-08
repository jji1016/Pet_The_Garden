package com.petthegarden.petthegarden.stray;

import com.petthegarden.petthegarden.entity.Stray;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class StrayDao {
    private final StrayRepository strayRepository;

    public void deleteAllInBatch() {
        strayRepository.deleteAllInBatch();
    }

    public int totalStray(String startDate,String endDate,String discvryPlc,String species) {
        return strayRepository.totalStray(startDate,endDate,discvryPlc,species);
    }

    public List<Stray> getStrayList(String startDate, String endDate, String discvryPlc, String species, int startItem, int endItem) {
        return strayRepository.getStrayList(startDate,endDate,discvryPlc,species,startItem,endItem);
    }
}
