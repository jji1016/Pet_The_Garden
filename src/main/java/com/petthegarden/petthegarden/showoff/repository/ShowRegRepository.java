package com.petthegarden.petthegarden.showoff.repository;

import com.petthegarden.petthegarden.entity.ShowOff;
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
}
