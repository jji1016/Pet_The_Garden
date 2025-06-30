package com.petthegarden.petthegarden.member;

import com.petthegarden.petthegarden.entity.Member;
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

    public void save(Member adminMember) {
    }
}
