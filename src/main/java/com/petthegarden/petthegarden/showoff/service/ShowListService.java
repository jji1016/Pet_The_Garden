package com.petthegarden.petthegarden.showoff.service;

import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.showoff.repository.ShowListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowListService {
    private final ShowListRepository showListRepository;

    public List<Pet> getAllPets() {
        return showListRepository.findAll();
    }

    public Pet getPetById(Integer id) {
        return showListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 펫입니다."));
    }
}
