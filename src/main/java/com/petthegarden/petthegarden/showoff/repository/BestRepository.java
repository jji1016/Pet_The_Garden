package com.petthegarden.petthegarden.showoff.repository;

import com.petthegarden.petthegarden.entity.ShowOff;
import com.petthegarden.petthegarden.showoff.dto.BestShowOffDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BestRepository extends JpaRepository<ShowOff, Integer> {

    /**
     * 지정된 월의 베스트 게시글 3개를 좋아요 수 기준으로 조회합니다.
     * Oracle 용 Native Query로 변경하였으며
     * 날짜 월 추출 시 EXTRACT + NVL 함수 사용,
     * 행 제한은 FETCH FIRST 3 ROWS ONLY 사용합니다.
     */
    @Query(value =
            "SELECT s.showOffID as id, s.subject, s.content, s.image, s.showOffLike, " +
                    "m.userName, p.petName, p.species, p.profileImg " +
                    "FROM ShowOff s " +
                    "JOIN Member m ON s.memberID = m.memberID " +
                    "JOIN Pet p ON s.petID = p.petID " +
                    "WHERE EXTRACT(MONTH FROM NVL(s.modifyDate, s.regDate)) = :month " +
                    "ORDER BY s.showOffLike DESC " +
                    "FETCH FIRST 3 ROWS ONLY",
            nativeQuery = true)
    List<Object[]> findTop3ByMonthOrderByLikesNative(@Param("month") int month);

    /**
     * 모든 베스트 게시글을 좋아요 수 기준으로 조회합니다.
     */
    @Query(value =
            "SELECT s.showOffID as id, s.subject, s.content, s.image, s.showOffLike, " +
                    "m.userName, p.petName, p.species, p.profileImg " +
                    "FROM ShowOff s " +
                    "JOIN Member m ON s.memberID = m.memberID " +
                    "JOIN Pet p ON s.petID = p.petID " +
                    "ORDER BY s.showOffLike DESC",
            nativeQuery = true)
    List<Object[]> findAllBestShowOffsNative();

    /**
     * Native Query 결과를 DTO로 변환하는 메서드
     */
    default List<BestShowOffDto> findTop3ByMonthOrderByLikes(int month) {
        List<Object[]> results = findTop3ByMonthOrderByLikesNative(month);
        return results.stream()
                .map(row -> new BestShowOffDto(
                        // 주의: oracle에서 숫자 타입은 Long인 경우가 있으니 필요 시 캐스팅 조정
                        ((Number) row[0]).intValue(),   // id
                        (String) row[1],                // subject
                        (String) row[2],                // content
                        (String) row[3],                // image
                        ((Number) row[4]).intValue(),  // showOffLike
                        (String) row[5],                // userName
                        (String) row[6],                // petName
                        (String) row[7],                // species
                        (String) row[8]                 // profileImg
                ))
                .toList();
    }

    /**
     * 모든 베스트 게시글을 DTO로 변환하는 메서드
     */
    default List<BestShowOffDto> findAllBestShowOffs() {
        List<Object[]> results = findAllBestShowOffsNative();
        return results.stream()
                .map(row -> new BestShowOffDto(
                        ((Number) row[0]).intValue(),
                        (String) row[1],
                        (String) row[2],
                        (String) row[3],
                        ((Number) row[4]).intValue(),
                        (String) row[5],
                        (String) row[6],
                        (String) row[7],
                        (String) row[8]
                ))
                .toList();
    }
}
