package com.petthegarden.petthegarden.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao memberDao;


    public void save(MemberDto memberDto) {
//        memberDao.save(memberDto);
    }
}
