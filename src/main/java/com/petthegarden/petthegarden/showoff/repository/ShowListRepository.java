package com.petthegarden.petthegarden.showoff.repository;

import com.petthegarden.petthegarden.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowListRepository extends JpaRepository<Pet, Integer> {
    // Pet 엔티티에 대한 기본 CRUD 제공
}
