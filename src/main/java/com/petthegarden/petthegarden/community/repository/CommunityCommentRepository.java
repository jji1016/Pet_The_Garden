package com.petthegarden.petthegarden.community.repository;

import com.petthegarden.petthegarden.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityCommentRepository extends JpaRepository<BoardComment,Integer> {

}
