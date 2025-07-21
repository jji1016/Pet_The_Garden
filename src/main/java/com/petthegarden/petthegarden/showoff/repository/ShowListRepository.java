package com.petthegarden.petthegarden.showoff.repository;

import com.petthegarden.petthegarden.entity.ShowOff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShowListRepository extends JpaRepository<ShowOff, Integer> {

    /**
     * 인기 게시글 3개 조회 (좋아요 수 기준 내림차순)
     * @return 좋아요 수 상위 3개 ShowOff 리스트
     */
    List<ShowOff> findTop3ByOrderByShowOffLikeDesc();

    /**
     * 전체 게시글 페이징 조회
     * @param pageable 페이징 정보
     * @return 페이징된 ShowOff 페이지
     */
    Page<ShowOff> findAll(Pageable pageable);

    /**
     * 특정 회원의 게시글 페이징 조회 (등록일 기준 내림차순)
     * @param memberId 회원 id
     * @param pageable 페이징 정보
     * @return 페이징된 회원별 ShowOff 페이지
     */
    Page<ShowOff> findByMember_IdOrderByRegDateDesc(Integer memberId, Pageable pageable);

}
