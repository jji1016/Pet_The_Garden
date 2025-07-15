package com.petthegarden.petthegarden.petnote.repository;

import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.petnote.dto.PetDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Integer> {


    List<Pet> findAllByMemberId(Integer memberID);

    Pet findFirstByMemberIdOrderByIdAsc(Integer memberID);

    @Query(value = "SELECT m.USERID " +
            "FROM MEMBER m " +
            "JOIN PET p ON m.MEMBERID = p.MEMBERID " +
            "WHERE p.PETID = :petID", nativeQuery = true)
    String findUserIDByPetID(@Param("petID") int petID);


    @Query("SELECT new com.petthegarden.petthegarden.petnote.dto.PetDto" +
            "(p.id, p.species, p.petName, p.birthDate, p.profileImg, p.petGender, p.member.userID) " +
            "FROM Pet p")
    List<PetDto> findAllPetDto();
}
