package com.petthegarden.petthegarden.showoff.service;

import com.petthegarden.petthegarden.showoff.dto.BestShowOffDto;
import com.petthegarden.petthegarden.showoff.repository.BestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BestService {

    private final BestRepository bestRepository;

    /**
     * 지정된 월의 베스트 게시글 3개를 조회합니다.
     * @param month 조회할 월 (1~12)
     * @return 베스트 게시글 DTO 리스트 (최대 3개)
     */
    public List<BestShowOffDto> getBestShowOffsByMonth(int month) {
        // 월 유효성 검증
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("월은 1부터 12 사이의 값이어야 합니다.");
        }

        return bestRepository.findTop3ByMonthOrderByLikes(month);
    }

    /**
     * 전체 베스트 게시글을 조회합니다.
     * @return 모든 월의 베스트 게시글 DTO 리스트
     */
    public List<BestShowOffDto> getAllBestShowOffs() {
        return bestRepository.findAllBestShowOffs();
    }
}
