package com.petthegarden.petthegarden.community.repository;

import com.petthegarden.petthegarden.community.dto.BoardDto;
import com.petthegarden.petthegarden.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Board, Integer> {
    List<Board> findByMemberId(Integer memberId);
}