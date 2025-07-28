package com.petthegarden.petthegarden.showoff.repository;

import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.entity.ShowOff;
import com.petthegarden.petthegarden.showoff.dto.ShowPetDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowRegRepository extends JpaRepository<ShowOff, Integer> {
    List<ShowOff> findByMemberIdOrderByRegDateDesc(Integer memberId);
    List<ShowOff> findByPetIdOrderByRegDateDesc(Integer petId);
    List<ShowOff> findBySubjectContainingIgnoreCaseOrderByRegDateDesc(String subject);
    List<ShowOff> findByContentContainingIgnoreCaseOrderByRegDateDesc(String content);
    List<ShowOff> findTop10ByOrderByShowOffLikeDescRegDateDesc();
    List<ShowOff> findTop10ByOrderByRegDateDesc();

    @Query("SELECT s FROM ShowOff s WHERE s.member.id = :memberId AND s.pet.id = :petId ORDER BY s.regDate DESC")
    List<ShowOff> findByMemberAndPet(@Param("memberId") Integer memberId, @Param("petId") Integer petId);

    @Query("SELECT s FROM ShowOff s LEFT JOIN FETCH s.showOffCommentList WHERE s.id = :id")
    Optional<ShowOff> findByIdWithComments(@Param("id") Integer id);

    @Query("SELECT new com.petthegarden.petthegarden.showoff.dto.ShowPetDto(" +
            "p.id, p.regDate, p.modifyDate, p.species, p.petName, p.birthDate, " +
            "p.profileImg, p.content, COUNT(f), p.character, p.petLike, p.petDisLike, " +
            "p.petGender, p.member.userID) " +
            "FROM Pet p " +
            "LEFT JOIN Follow f ON p.id = f.pet.id " +
            "WHERE p.member.id = :memberId " +
            "GROUP BY p.id, p.regDate, p.modifyDate, p.species, p.petName, p.birthDate, " +
            "p.profileImg, p.content, p.character, p.petLike, p.petDisLike, " +
            "p.petGender, p.member.userID")
    List<ShowPetDto> getPetList(@Param("memberId") Integer memberId);


    @Query(value = "SELECT * FROM PET WHERE PETID = :petID",
            nativeQuery = true)
    Pet getPetEntityList(@Param("petID") Integer petID);
}
