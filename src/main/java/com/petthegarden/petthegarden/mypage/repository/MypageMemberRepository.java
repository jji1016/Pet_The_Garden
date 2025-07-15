package com.petthegarden.petthegarden.mypage.repository;

import com.petthegarden.petthegarden.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MypageMemberRepository extends JpaRepository<Member,Integer> {
    Optional<Member> findByUserID(String userID);

    //회원 탈퇴
    @Modifying
    @Query(value = "UPDATE MEMBER SET deleteStatus = 1 WHERE MEMBERID = :id",nativeQuery = true)
    int deleteAccount(@Param("id") Integer id);
}
