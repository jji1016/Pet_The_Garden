package com.petthegarden.petthegarden.member;

import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.mypage.repository.MypageMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberDao {
    private final MypageMemberRepository mypageMemberRepository;
    private final MemberRepository memberRepository;

    public Optional<Member> findByUserID(String userID) {
        return mypageMemberRepository.findByUserID(userID);
    }

    public void save(Member adminMember) {
        memberRepository.save(adminMember);
    }
    public int deleteAccount(Integer id) {
        return mypageMemberRepository.deleteAccount(id);
    }
    public Optional<Member> findById(Integer id) {
        return mypageMemberRepository.findById(id);
    }
}
