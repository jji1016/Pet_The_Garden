package com.petthegarden.petthegarden.showoff.repository;// ShowListRepository.java
import com.petthegarden.petthegarden.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShowListRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findTop3ByOrderByPetLikeDesc();
}
