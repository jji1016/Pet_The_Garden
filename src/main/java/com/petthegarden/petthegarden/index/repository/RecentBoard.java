package com.petthegarden.petthegarden.index.repository;

import com.petthegarden.petthegarden.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecentBoard extends JpaRepository<Board, Integer> {

    @Query(value = "SELECT * FROM (SELECT * FROM board ORDER BY regdate DESC) WHERE ROWNUM <= 10", nativeQuery = true)
    List<Board> findRecentBoards();

}
