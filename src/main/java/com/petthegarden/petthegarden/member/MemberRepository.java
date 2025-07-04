package com.petthegarden.petthegarden.member;

import com.petthegarden.petthegarden.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Integer> {
    Optional<Member> findByUserID(String userID);

    boolean existsByUserID(String userID);

    boolean existsByUserName(String userName);
}
