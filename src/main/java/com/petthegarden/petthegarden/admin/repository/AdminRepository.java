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
            "p.id, p.profileImg, p.petName, p.species, p.follow, p.petGender, p.regDate) " +
            "FROM Pet p " +
            "WHERE (:startDate IS NULL OR p.regDate >= TO_DATE(:startDate, 'YYYY-MM-DD')) " +
            "AND (:endDate IS NULL OR p.regDate <= TO_DATE(:endDate, 'YYYY-MM-DD')) " +
            "AND ((:key = 'petName' AND p.petName LIKE %:search%) OR " +
            "(:key = 'species' AND p.species LIKE %:search%) OR " +
            "(:key IS NULL OR :key = '')" +
            ")")
    Page<AdminPetDto> getPetList(@Param("startDate") String startDate,
                                       @Param("endDate") String endDate,
                                       @Param("key") String key,
                                       @Param("search") String search,
                                       Pageable pageable);


    // ReportType 있을시
    @Query("""
SELECT new com.petthegarden.petthegarden.admin.dto.AdminReportDto(
    r.Id, r.reportDate, r.reason, r.type, r.repID, m.userName,
    CASE
        WHEN r.type = 'FREE_POST' THEN b.subject
        WHEN r.type = 'SHOWOFF_POST' THEN s.subject
        WHEN r.type = 'FREE_COMMENT' THEN b.subject
        WHEN r.type = 'SHOWOFF_COMMENT' THEN s.subject
        ELSE ''
    END,
    CASE
        WHEN r.type = 'FREE_POST' THEN b.member.userName
        WHEN r.type = 'SHOWOFF_POST' THEN s.member.userName
        WHEN r.type = 'FREE_COMMENT' THEN bc.member.userName
        WHEN r.type = 'SHOWOFF_COMMENT' THEN sc.member.userName
        ELSE ''
    END
)
FROM Report r
JOIN r.member m
LEFT JOIN Board b ON r.repID = b.Id AND r.type = 'FREE_POST'
LEFT JOIN BoardComment bc ON r.repID = bc.Id AND r.type = 'FREE_COMMENT'
LEFT JOIN ShowOff s ON r.repID = s.Id AND r.type = 'SHOWOFF_POST'
LEFT JOIN ShowOffComment sc ON r.repID = sc.Id AND r.type = 'SHOWOFF_COMMENT'
WHERE r.type = :type
AND (:startDate IS NULL OR r.reportDate >= :startDate)
AND (:endDate IS NULL OR r.reportDate <= :endDate)
AND (
    :search IS NULL OR :search = '' OR
    (
        (:key = 'reporter' AND (
            LOWER(r.member.userName) LIKE LOWER(CONCAT('%', :search, '%'))
        )) OR
        (:key = 'writer' AND (
            (r.type = 'FREE_POST' AND LOWER(b.member.userName) LIKE LOWER(CONCAT('%', :search, '%'))) OR
            (r.type = 'SHOWOFF_POST' AND LOWER(s.member.userName) LIKE LOWER(CONCAT('%', :search, '%'))) OR
            (r.type = 'FREE_COMMENT' AND LOWER(bc.member.userName) LIKE LOWER(CONCAT('%', :search, '%'))) OR
            (r.type = 'SHOWOFF_COMMENT' AND LOWER(sc.member.userName) LIKE LOWER(CONCAT('%', :search, '%')))
        )) OR
        (:key = 'subject' AND (
            (r.type = 'FREE_POST' AND LOWER(b.subject) LIKE LOWER(CONCAT('%', :search, '%'))) OR
            (r.type = 'SHOWOFF_POST' AND LOWER(s.subject) LIKE LOWER(CONCAT('%', :search, '%')))
        ))
    )
)
""")
    Page<AdminReportDto> getFilteredReports(
            @Param("type") ReportType type,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("key") String key,
            @Param("search") String search,
            Pageable pageable
    );


    // ReportType All일 경우
    @Query("""
SELECT new com.petthegarden.petthegarden.admin.dto.AdminReportDto(
    r.Id, r.reportDate, r.reason, r.type, r.repID, m.userName,
    CASE
        WHEN r.type = 'FREE_POST' THEN b.subject
        WHEN r.type = 'SHOWOFF_POST' THEN s.subject
        WHEN r.type = 'FREE_COMMENT' THEN b.subject
        WHEN r.type = 'SHOWOFF_COMMENT' THEN s.subject
        ELSE ''
    END,
    CASE
        WHEN r.type = 'FREE_POST' THEN b.member.userName
        WHEN r.type = 'SHOWOFF_POST' THEN s.member.userName
        WHEN r.type = 'FREE_COMMENT' THEN bc.member.userName
        WHEN r.type = 'SHOWOFF_COMMENT' THEN sc.member.userName
        ELSE ''
    END
)
FROM Report r
JOIN r.member m
LEFT JOIN Board b ON r.repID = b.Id AND r.type = 'FREE_POST'
LEFT JOIN BoardComment bc ON r.repID = bc.Id AND r.type = 'FREE_COMMENT'
LEFT JOIN ShowOff s ON r.repID = s.Id AND r.type = 'SHOWOFF_POST'
LEFT JOIN ShowOffComment sc ON r.repID = sc.Id AND r.type = 'SHOWOFF_COMMENT'
WHERE (:startDate IS NULL OR r.reportDate >= :startDate)
AND (:endDate IS NULL OR r.reportDate <= :endDate)
AND (
    :search IS NULL OR :search = '' OR
    (
        (:key = 'reporter' AND (
            LOWER(r.member.userName) LIKE LOWER(CONCAT('%', :search, '%'))
        )) OR
        (:key = 'writer' AND (
            (r.type = 'FREE_POST' AND LOWER(b.member.userName) LIKE LOWER(CONCAT('%', :search, '%'))) OR
            (r.type = 'SHOWOFF_POST' AND LOWER(s.member.userName) LIKE LOWER(CONCAT('%', :search, '%'))) OR
            (r.type = 'FREE_COMMENT' AND LOWER(bc.member.userName) LIKE LOWER(CONCAT('%', :search, '%'))) OR
            (r.type = 'SHOWOFF_COMMENT' AND LOWER(sc.member.userName) LIKE LOWER(CONCAT('%', :search, '%')))
        )) OR
        (:key = 'subject' AND (
            (r.type = 'FREE_POST' AND LOWER(b.subject) LIKE LOWER(CONCAT('%', :search, '%'))) OR
            (r.type = 'SHOWOFF_POST' AND LOWER(s.subject) LIKE LOWER(CONCAT('%', :search, '%')))
        ))
    )
)
""")
    Page<AdminReportDto> getAllReports(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("key") String key,
            @Param("search") String search,
            Pageable pageable
    );

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
}
