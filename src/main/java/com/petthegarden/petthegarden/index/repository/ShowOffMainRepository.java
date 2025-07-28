package com.petthegarden.petthegarden.index.repository;

import com.petthegarden.petthegarden.entity.ShowOff;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;


public interface ShowOffMainRepository extends JpaRepository<ShowOff, Integer> {

    @Query("SELECT s FROM ShowOff s " +
            "WHERE s.regDate BETWEEN :startOfWeek AND :endOfWeek " +
            "ORDER BY s.showOffLike DESC")
    List<ShowOff> findTop3ByThisWeek(@Param("startOfWeek") LocalDateTime startOfWeek,
                                     @Param("endOfWeek") LocalDateTime endOfWeek,
                                     Pageable pageable);

}
