package com.petthegarden.petthegarden.petnote.dao;

import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.petnote.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PetDao {
    private final PetRepository petRepository;

    public List<Pet> getPetList(Integer memberID) {
        return petRepository.findAllByMemberId(memberID);
    }

    public Pet findFirstPet(Integer memberID) {
        return petRepository.findFirstByMemberIdOrderByIdAsc(memberID);
    }

}
