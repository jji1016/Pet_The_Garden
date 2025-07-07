package com.petthegarden.petthegarden.petnote.repository;

import com.petthegarden.petthegarden.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet,Integer> {

    List<Pet> findFirstByMemberId(Integer memberId);
}
