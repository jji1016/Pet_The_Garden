package com.petthegarden.petthegarden.config;

import com.petthegarden.petthegarden.constant.Gender;
import com.petthegarden.petthegarden.constant.Role;
import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.member.MemberDao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {
    //application시작할때 동작한다.
    private final MemberDao memberDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        String adminID = "admin";
        Optional<Member> optionalMember = memberDao.findByUserID(adminID);
        if(!optionalMember.isPresent()) {
            Member adminMember = Member.builder()
                    .userID(adminID)
                    .role(Role.ROLE_ADMIN)
                    .userName("관리자")
                    .email("admin@hanmail.net")
                    .tel("010-8765-4321")
                    .address01("adminAddress01")
                    .zipcode(54321)
                    .userPW(bCryptPasswordEncoder.encode("1234"))
                    .build();
            memberDao.save(adminMember);
        } else {
            System.out.println("관리자 계정이 이미 있습니다.");
        }
        Optional<Member> hongMember = memberDao.findByUserID("user");
        if(!hongMember.isPresent()) {
            Member member1 = Member.builder()
                    .userID("user")
                    .userName("userName")
                    .email("user@naver.com")
                    .tel("010-1234-5678")
                    .address01("userAddress01")
                    .zipcode(12345)
                    .userPW(bCryptPasswordEncoder.encode("1234"))
                    .role(Role.ROLE_USER)
                    .build();
            memberDao.save(member1);
        }

    }
}
