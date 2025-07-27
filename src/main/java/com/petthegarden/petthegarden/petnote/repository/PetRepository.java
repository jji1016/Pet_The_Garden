package com.petthegarden.petthegarden.petnote.repository;

import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.petnote.dto.PetDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Integer> {


    List<Pet> findAllByMember_Id(Integer memberID);


    Pet findFirstByMemberIdOrderByIdAsc(Integer memberID);

    @Query(value = "SELECT m.USERID " +
            "FROM MEMBER m " +
            "JOIN PET p ON m.MEMBERID = p.MEMBERID " +
            "WHERE p.PETID = :petID", nativeQuery = true)
    String findUserIDByPetID(@Param("petID") int petID);


    @Query("SELECT new com.petthegarden.petthegarden.petnote.dto.PetDto" +
            "(p.id, p.species, p.petName, p.birthDate, p.profileImg, p.petGender, p.member.userID) " +
            "FROM Pet p ORDER BY p.regDate DESC")
    List<PetDto> findAllPetDto();

    @Query("SELECT new com.petthegarden.petthegarden.petnote.dto.PetDto" +
            "(p.id, p.species, p.petName, p.birthDate, p.profileImg, p.petGender, p.member.userID) " +
            "FROM Pet p ORDER BY p.regDate DESC")
    Page<PetDto> findAllPetDtoPaged(Pageable pageable);


    @Query("SELECT p.member.id FROM Pet p WHERE p.id = :petID")
    Integer findMemberIDByPetID(@Param("petID") Integer petID);


//    @Query("SELECT d.pet.id FROM Diary d WHERE d.diaryID = :diaryID")
//    Integer getPetIDByDiaryID(@Param("diaryID") Integer diaryID);

    @Query("SELECT d.pet.id FROM Diary d WHERE d.Id = :diaryID")
    Integer getPetIDByDiaryID(@Param("diaryID") Integer diaryID);

}
