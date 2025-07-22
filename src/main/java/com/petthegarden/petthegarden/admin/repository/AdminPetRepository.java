package com.petthegarden.petthegarden.admin.repository;

import com.petthegarden.petthegarden.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminPetRepository extends JpaRepository<Pet,Integer> {
    List<Pet> findByMember_Id(Integer memberID);
}
