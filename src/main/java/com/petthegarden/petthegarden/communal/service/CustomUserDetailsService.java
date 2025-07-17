package com.petthegarden.petthegarden.communal.service;

import com.petthegarden.petthegarden.communal.dto.CustomUserDetails;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.member.MemberDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberDao memberDao;

    @Override
    public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {
        log.info("userID==={}",userID);
        Optional<Member> optionalMember = memberDao.findByUserID(userID);

        if (optionalMember.isEmpty()) {
            throw new UsernameNotFoundException("존재하지 않는 계정입니다.");
        }

        Member member = optionalMember.get();

        if (member.isDeleteStatus()) {
            throw new UsernameNotFoundException("탈퇴한 계정입니다.");
        }

        return new CustomUserDetails(member);
    }
}
