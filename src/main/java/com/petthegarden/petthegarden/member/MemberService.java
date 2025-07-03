package com.petthegarden.petthegarden.member;

import com.petthegarden.petthegarden.constant.Role;
import com.petthegarden.petthegarden.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao memberDao;


    public void save(MemberDto memberDto) {
        memberDto.setRole(Role.ROLE_USER);
        Member savedMember = memberDto.toMember();
        memberDao.save(savedMember);
    }
}
