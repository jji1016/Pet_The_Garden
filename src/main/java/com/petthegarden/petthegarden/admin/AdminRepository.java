package com.petthegarden.petthegarden.admin;

import com.petthegarden.petthegarden.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Member, Integer> {

    @Query(value = "SELECT COUNT(*) FROM MEMBER WHERE TRUNC(regDate) >= TRUNC(ADD_MONTHS(SYSDATE, -1))",
    nativeQuery = true)
    int newMember();

    @Query(value = "SELECT COUNT(*) FROM MEMBER ",
    nativeQuery = true)
    int totalMember();

    @Query(value = "SELECT COUNT(*) FROM PET ",
    nativeQuery = true)
    int totalPet();

    @Query(value = "SELECT COUNT(*) FROM PET WHERE TRUNC(regDate) >= TRUNC(ADD_MONTHS(SYSDATE, -1))",
    nativeQuery = true)
    int recentPet();

    @Query(value = "SELECT COUNT(*) FROM STRAY WHERE TRUNC(TO_DATE(PBLANC_BEGIN_DE, 'YYYY-MM-DD')) >= TRUNC(ADD_MONTHS(SYSDATE, -1))",
    nativeQuery = true)
    int recentStray();

    @Query(value = "SELECT COUNT(*) FROM SHOWOFF WHERE TRUNC(regDate) >= TRUNC(ADD_MONTHS(SYSDATE, -1))",
    nativeQuery = true)
    int recentShowOff();

    @Query(value = "SELECT COUNT(*) FROM BOARD WHERE TRUNC(regDate) >= TRUNC(ADD_MONTHS(SYSDATE, -1))",
    nativeQuery = true)
    int recentBoard();
}
