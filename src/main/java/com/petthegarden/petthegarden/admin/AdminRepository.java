package com.petthegarden.petthegarden.admin;

import com.petthegarden.petthegarden.admin.dto.AdminMemberDto;
import com.petthegarden.petthegarden.admin.dto.AdminShowOffDto;
import com.petthegarden.petthegarden.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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

    @Query("SELECT new com.petthegarden.petthegarden.admin.dto.AdminShowOffDto(" +
            "s.showOffLike, s.subject, m.userName, s.regDate, s.Id) " +
            "FROM ShowOff s " +
            "JOIN s.member m " +
            "ORDER BY s.showOffLike DESC")
    List<AdminShowOffDto> getShowOffList(Pageable pageable);

    @Query("SELECT new com.petthegarden.petthegarden.admin.dto.AdminMemberDto(" +
            "m.id, m.userID, m.userPW, m.userName, m.image, m.email, m.tel, " +
            "m.regDate, m.zipcode, m.address01, m.address02) " +
            "FROM Member m")
    List<AdminMemberDto> getMemberList();
}
