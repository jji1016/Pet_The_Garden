package com.petthegarden.petthegarden.showoff.repository;

import com.petthegarden.petthegarden.entity.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShowListRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findTop3ByOrderByPetLikeDesc();
    Page<Pet> findByIdNotIn(List<Integer> ids, Pageable pageable);
}



