package com.petthegarden.petthegarden.community.service;

import com.petthegarden.petthegarden.community.dao.CommunityDao;
import com.petthegarden.petthegarden.community.dao.CommunityDao;
import com.petthegarden.petthegarden.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityDao communityDao;

    public List<Board> getBoardList() {
        return communityDao.findAllBoards();
    }

    public Board getBoardById(Integer id) {
        return communityDao.findById(id);
    }
}