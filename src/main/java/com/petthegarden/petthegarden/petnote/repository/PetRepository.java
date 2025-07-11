package com.petthegarden.petthegarden.petnote.repository;

import com.petthegarden.petthegarden.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet,Integer> {


    List<Pet> findAllByMemberId(Integer memberID);

    Pet findFirstByMemberIdOrderByIdAsc(Integer memberID);

}
