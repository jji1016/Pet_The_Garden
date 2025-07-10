package com.petthegarden.petthegarden.showoff.repository;

import com.petthegarden.petthegarden.entity.ShowOff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ShowListRepository extends JpaRepository<ShowOff, Integer> {

    // 인기 게시글 3개 (펫 좋아요 순으로 정렬)
    @Query("SELECT s FROM ShowOff s ORDER BY s.pet.petLike DESC")
    List<ShowOff> findTop3ByOrderByPetLikeDesc();

    // 전체 게시글 페이징
    Page<ShowOff> findAll(Pageable pageable);
}
