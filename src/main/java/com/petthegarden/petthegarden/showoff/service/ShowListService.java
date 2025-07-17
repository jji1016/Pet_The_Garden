package com.petthegarden.petthegarden.showoff.service;

import com.petthegarden.petthegarden.entity.ShowOff;
import com.petthegarden.petthegarden.showoff.repository.ShowListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowListService {

    private final ShowListRepository showListRepository;

    /**
     * 인기 게시글 3개 가져오기 (showOffLike 좋아요 순으로 정렬)
     */
    public List<ShowOff> getPopularPosts() {
        return showListRepository.findTop3ByOrderByShowOffLikeDesc();
    }

    /**
     * 전체 게시글 페이징 처리
     */
    public Page<ShowOff> getAllShowOffs(Pageable pageable) {
        return showListRepository.findAll(pageable);
    }

    /**
     * 특정 회원의 게시글 페이징 처리
     */
    public Page<ShowOff> getShowOffsByMember(Integer memberId, Pageable pageable) {
        return showListRepository.findByMember_IdOrderByRegDateDesc(memberId, pageable);
    }
}
