package com.petthegarden.petthegarden.community.dao;

import com.petthegarden.petthegarden.community.dto.BoardDto;
import com.petthegarden.petthegarden.community.repository.CommunityRepository;
import com.petthegarden.petthegarden.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommunityDao {

    private final CommunityRepository communityRepository;

    public List<Board> findAllBoards() {
        return communityRepository.findAll();
    }

    public Board findById(Integer id) {
        return communityRepository.findById(id).orElse(null);
    }

    public List<Board> findByMemberId(Integer memberId){return communityRepository.findByMemberId(memberId);}

}