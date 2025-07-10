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

    // 특정 회원의 장기자랑 목록 조회
    List<ShowOff> findByMemberIdOrderByRegDateDesc(Integer memberId);

    // 특정 반려동물의 장기자랑 목록 조회
    List<ShowOff> findByPetIdOrderByRegDateDesc(Integer petId);

    // 제목으로 검색
    List<ShowOff> findBySubjectContainingIgnoreCaseOrderByRegDateDesc(String subject);

    // 내용으로 검색
    List<ShowOff> findByContentContainingIgnoreCaseOrderByRegDateDesc(String content);

    // 인기순 조회 (좋아요 수 기준)
    List<ShowOff> findTop10ByOrderByShowOffLikeDescRegDateDesc();

    // 최신순 조회
    List<ShowOff> findTop10ByOrderByRegDateDesc();

    // 특정 회원과 반려동물의 장기자랑 조회
    @Query("SELECT s FROM ShowOff s WHERE s.member.id = :memberId AND s.pet.id = :petId ORDER BY s.regDate DESC")
    List<ShowOff> findByMemberAndPet(@Param("memberId") Integer memberId, @Param("petId") Integer petId);

    // 장기자랑 상세 조회 (댓글 포함)
    @Query("SELECT s FROM ShowOff s LEFT JOIN FETCH s.showOffCommentList WHERE s.id = :id")
    Optional<ShowOff> findByIdWithComments(@Param("id") Integer id);
}
