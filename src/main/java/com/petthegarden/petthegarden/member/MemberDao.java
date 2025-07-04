package com.petthegarden.petthegarden.member;

import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.member.dto.LoginDto;
import com.petthegarden.petthegarden.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberDao {
    private final MemberRepository memberRepository;

    public Optional<Member> findByUserID(String userID) {
        return memberRepository.findByUserID(userID);
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public boolean existsByUserID(String userID) {
        return memberRepository.existsByUserID(userID);
    }

    public boolean existsByUserName(String userName) {
        return memberRepository.existsByUserName(userName);
    }

}
