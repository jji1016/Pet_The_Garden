package com.petthegarden.petthegarden.admin.repository;

import com.petthegarden.petthegarden.admin.dto.*;
import com.petthegarden.petthegarden.constant.ReportType;
import com.petthegarden.petthegarden.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
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
            "m.id, m.userID, m.userName, m.regDate, COUNT(DISTINCT p.id), COUNT(DISTINCT b.Id)) " +
            "FROM Member m " +
            "LEFT JOIN Pet p ON m.id = p.member.id " +
            "LEFT JOIN Board b ON m.id = b.member.id " +
            "WHERE (:startDate IS NULL OR m.regDate >= TO_DATE(:startDate, 'YYYY-MM-DD')) " +
            "AND (:endDate IS NULL OR m.regDate <= TO_DATE(:endDate, 'YYYY-MM-DD')) " +
            "AND ((:key = 'userID' AND m.userID LIKE %:search%) OR " +
            "(:key = 'userName' AND m.userName LIKE %:search%) OR " +
            "(:key IS NULL OR :key = '')) " +
            "AND m.deleteStatus is false " +
            "GROUP BY m.id, m.userID, m.userName, m.regDate")
    Page<AdminMemberDto> getMemberList(@Param("startDate") String startDate,
                                               @Param("endDate") String endDate,
                                               @Param("key") String key,
                                               @Param("search") String search,
                                               Pageable pageable);

    @Query("SELECT new com.petthegarden.petthegarden.admin.dto.AdminPetDto(" +
            "p.id, p.profileImg, p.petName, p.species, COUNT(f.Id), p.petGender, p.regDate) " +
            "FROM Pet p " +
            "LEFT JOIN Follow f ON p.id = f.pet.id " +
            "WHERE (:startDate IS NULL OR p.regDate >= TO_DATE(:startDate, 'YYYY-MM-DD')) " +
            "AND (:endDate IS NULL OR p.regDate <= TO_DATE(:endDate, 'YYYY-MM-DD')) " +
            "AND ((:key = 'petName' AND p.petName LIKE %:search%) OR " +
            "(:key = 'species' AND p.species LIKE %:search%) OR " +
            "(:key IS NULL OR :key = '')) " +
            "GROUP BY p.id, p.profileImg, p.petName, p.species, p.petGender, p.regDate")
    Page<AdminPetDto> getPetList(@Param("startDate") String startDate,
                                 @Param("endDate") String endDate,
                                 @Param("key") String key,
                                 @Param("search") String search,
                                 Pageable pageable);



    @Query("SELECT new com.petthegarden.petthegarden.admin.dto.AdminReportDto(" +
            "r.Id, r.reportDate, r.reason, r.type, b.Id, r.member.userName,b.subject, b.member.userName) " +
            "FROM Report r " +
            "LEFT JOIN Member m ON r.member.id = m.id " +
            "LEFT JOIN Board b ON (r.repID = b.Id AND r.type = 'FREE_POST' ) " +
            "WHERE (:startDate IS NULL OR r.reportDate >= :startDate) " +
            "AND (:endDate IS NULL OR r.reportDate <= :endDate) " +
            "AND ((:key = 'reporter' AND m.userName LIKE %:search%) OR " +
            "(:key = 'subject' AND b.subject LIKE %:search%) OR " +
            "(:key = 'writer' AND b.member.userName LIKE %:search%) OR " +
            "(:key IS NULL OR :key = '')" +
            ") " +
            "ORDER BY r.reportDate DESC ")
    Page<AdminReportDto> getReportBoardList(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate")LocalDateTime endDate,
                                       @Param("key")String key,
                                       @Param("search")String search,
                                       Pageable pageable);

    @Query("SELECT new com.petthegarden.petthegarden.admin.dto.AdminReportDto(" +
            "r.Id, r.reportDate, r.reason, r.type, bc.Id, r.member.userName,bc.board.subject, bc.member.userName) " +
            "FROM Report r " +
            "LEFT JOIN Member m ON r.member.id = m.id " +
            "LEFT JOIN BoardComment bc ON (r.repID = bc.Id AND r.type = 'FREE_COMMENT' ) " +
            "WHERE (:startDate IS NULL OR r.reportDate >= :startDate) " +
            "AND (:endDate IS NULL OR r.reportDate <= :endDate) " +
            "AND ((:key = 'reporter' AND m.userName LIKE %:search%) OR " +
            "(:key = 'subject' AND bc.board.subject LIKE %:search%) OR " +
            "(:key = 'writer' AND bc.member.userName LIKE %:search%) OR " +
            "(:key IS NULL OR :key = '')" +
            ") " +
            "ORDER BY r.reportDate DESC ")
    Page<AdminReportDto> getReportCommentList(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate")LocalDateTime endDate,
                                       @Param("key")String key,
                                       @Param("search")String search,
                                       Pageable pageable);


    @Query("SELECT new com.petthegarden.petthegarden.admin.dto.AdminShowOffBoardDto( " +
            "s.Id, s.subject, s.regDate, count(sc.Id)) " +
            "FROM ShowOff s " +
            "LEFT JOIN ShowOffComment sc ON s.Id = sc.showOff.Id " +
            "WHERE s.member.id = :memberID " +
            "GROUP BY s.Id, s.subject, s.regDate ")
    List<AdminShowOffBoardDto> getShowOffBoardList(@Param("memberID") Integer memberID);

    @Query("SELECT new com.petthegarden.petthegarden.admin.dto.AdminShowOffCommentDto( " +
            "s.Id, sc.content, sc.regDate, s.subject) " +
            "FROM ShowOffComment sc " +
            "LEFT JOIN ShowOff s ON sc.showOff.Id = s.Id " +
            "WHERE sc.member.id = :memberID ")
    List<AdminShowOffCommentDto> getShowOffCommentList(@Param("memberID") Integer memberID);

    @Query("SELECT new com.petthegarden.petthegarden.admin.dto.AdminFreeBoardDto( " +
            "b.Id, b.subject, b.regDate, count(bc.Id)) " +
            "FROM Board b " +
            "LEFT JOIN BoardComment bc ON b.Id = bc.board.Id " +
            "WHERE b.member.id = :memberID " +
            "GROUP BY b.Id, b.subject, b.regDate ")
    List<AdminFreeBoardDto> getFreeBoardList(@Param("memberID") Integer memberID);

    @Query("SELECT new com.petthegarden.petthegarden.admin.dto.AdminFreeCommentDto( " +
            "b.Id, bc.content, bc.regDate, b.subject) " +
            "FROM BoardComment bc " +
            "LEFT JOIN Board b ON bc.board.Id = b.Id " +
            "WHERE bc.member.id = :memberID ")
    List<AdminFreeCommentDto> getFreeCommentList(@Param("memberID") Integer memberID);

    @Query(value = "SELECT SPECIES, COUNT(*) " +
            "FROM PET " +
            "GROUP BY SPECIES " +
            "ORDER BY COUNT(*) DESC ",
            nativeQuery = true)
    List<Object[]> getSpeciesChart();

}
