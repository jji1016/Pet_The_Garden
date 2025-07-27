package com.petthegarden.petthegarden.petnote.dao;

import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.petnote.dto.PetDto;
import com.petthegarden.petthegarden.petnote.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PetDao {
    private final PetRepository petRepository;

    public List<Pet> getPetList(Integer memberID) {
        return petRepository.findAllByMember_Id(memberID);
    }

    public Pet findFirstPet(Integer memberID) {
        return petRepository.findFirstByMemberIdOrderByIdAsc(memberID);
    }

    public Optional<Pet> findPetByPetID(Integer petID) {
        return petRepository.findById(petID);
    }


    public String getUserIDByPetID(int petID) {
        return petRepository.findUserIDByPetID(petID);
    }

    public List<PetDto> findAllPetDto() {
        return petRepository.findAllPetDto();
    }

    public Page<PetDto> findAllPetDtoPaged(Pageable pageable) {
        return petRepository.findAllPetDtoPaged(pageable);
    }

    public Integer findMemberIDByPetID(Integer petID) {
        return petRepository.findMemberIDByPetID(petID);
    }

    public Optional<Pet> findById(Integer petID) {
        return petRepository.findById(petID);
    }


}
