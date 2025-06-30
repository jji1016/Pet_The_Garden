package com.petthegarden.petthegarden.mypage.service;

import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.mypage.dao.MypageDao;
import com.petthegarden.petthegarden.mypage.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MypageService {
    private final MypageDao mypageDao;

    public MemberDto findByUserID(String userID) {
        Optional<Member> optionalMember = mypageDao.findByUserID(userID);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return member.toMemberDto();
        } else {
            return null;
        }
    }

    public void updateInfo(MemberDto memberDto) {
        Member member = mypageDao.findById(memberDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        member.updateInfo(
                memberDto.getUserPW(),
                memberDto.getUserEmail(),
                memberDto.getTel(),
                memberDto.getZipcode(),
                memberDto.getAddress01(),
                memberDto.getAddress02()
        );
    }
}