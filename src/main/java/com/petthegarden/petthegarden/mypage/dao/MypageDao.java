package com.petthegarden.petthegarden.mypage.dao;

import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import com.petthegarden.petthegarden.mypage.repository.MypageMemberRepository;
import com.petthegarden.petthegarden.mypage.repository.MypagePetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MypageDao {
    private final MypageMemberRepository mypageMemberRepository;
    private final MypagePetRepository mypagePetRepository;

    public Optional<Member> findByUserID(String userID) {
        return mypageMemberRepository.findByUserID(userID);
    }
    public void save(Member member) {
        mypageMemberRepository.save(member);
    }

    public int deleteAccount(Integer id) {
        return mypageMemberRepository.deleteAccount(id);
    }
    public Optional<Member> findById(Integer id) {
        return mypageMemberRepository.findById(id);
    }

    public void savePet(Pet pet) {
        mypagePetRepository.save(pet);
    }
}
