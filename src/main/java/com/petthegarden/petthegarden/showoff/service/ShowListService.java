package com.petthegarden.petthegarden.showoff.service;

import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.showoff.repository.ShowListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowListService {
    private final ShowListRepository showListRepository;

    public List<Pet> getTop3PopularPets() {
        return showListRepository.findTop3ByOrderByPetLikeDesc();
    }

    public Page<Pet> getPetsByPage(Pageable pageable) {
        return showListRepository.findAll(pageable);
    }


    public Pet getPetById(Integer id) {
        return showListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 펫입니다."));
    }
}

