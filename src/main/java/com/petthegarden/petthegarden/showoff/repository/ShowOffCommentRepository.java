package com.petthegarden.petthegarden.showoff.repository;

import com.petthegarden.petthegarden.entity.ShowOffComment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShowOffCommentRepository extends JpaRepository<ShowOffComment, Integer> {
    List<ShowOffComment> findByShowOff_IdOrderByRegDateDesc(Integer showoffId);
}
