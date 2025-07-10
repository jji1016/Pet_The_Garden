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

    public List<ShowOff> getTop3PopularShowOffs() {
        List<ShowOff> allShowOffs = showListRepository.findTop3ByOrderByPetLikeDesc();
        return allShowOffs.stream().limit(3).toList();
    }

    public Page<ShowOff> getShowOffsByPage(Pageable pageable) {
        return showListRepository.findAll(pageable);
    }

    public ShowOff getShowOffById(Integer id) {
        return showListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }
}
