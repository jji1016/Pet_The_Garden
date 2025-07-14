package com.petthegarden.petthegarden.community.repository;

import com.petthegarden.petthegarden.community.dto.BoardDto;
import com.petthegarden.petthegarden.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Board, Integer> {
    List<Board> findByMemberId(Integer memberId);

    @Query("SELECT b FROM Board b WHERE LOWER(b.subject) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(b.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Board> findBySubjectContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Board> findByMemberId(Integer memberId, Pageable pageable);
}