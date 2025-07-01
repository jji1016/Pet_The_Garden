package com.petthegarden.petthegarden.community.repository;

import com.petthegarden.petthegarden.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Board, Integer> {

}
