package com.petthegarden.petthegarden.stray;

import com.petthegarden.petthegarden.entity.Stray;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StrayRepository extends JpaRepository<Stray, Integer> {

    @Query(value = "SELECT COUNT(*) FROM Stray " +
            "WHERE " +
            "((:startDate IS NULL AND :endDate IS NULL) OR " +
            "(:startDate <= PBLANC_BEGIN_DE AND :endDate >= PBLANC_BEGIN_DE)) " +
            "AND (:discvryPlc IS NULL OR DISCVRY_PLC_INFO LIKE %:discvryPlc%) " +
            "AND (:species IS NULL OR SPECIES_NM LIKE %:species%) ",
            nativeQuery = true)
    int totalStray(@Param("startDate") String startDate,
                   @Param("endDate") String endDate,
                   @Param("discvryPlc") String discvryPlc,
                   @Param("species") String species);

    @Query(value = "SELECT * FROM (" +
            "  SELECT inner_query.*, ROW_NUMBER() OVER (ORDER BY PBLANC_BEGIN_DE DESC, ABDM_IDNTFY_NO) AS rn FROM Stray inner_query " +
            "  WHERE " +
            "    ((:startDate IS NULL AND :endDate IS NULL) OR " +
            "    (:startDate <= PBLANC_BEGIN_DE AND :endDate >= PBLANC_BEGIN_DE)) " +
            "    AND (:discvryPlc IS NULL OR DISCVRY_PLC_INFO LIKE %:discvryPlc%) " +
            "    AND (:species IS NULL OR SPECIES_NM LIKE %:species%) " +
            ") WHERE rn BETWEEN :startItem AND :endItem",
            nativeQuery = true)
    List<Stray> getStrayList(@Param("startDate") String startDate,
                             @Param("endDate") String endDate,
                             @Param("discvryPlc") String discvryPlc,
                             @Param("species") String species,
                             @Param("startItem") int startItem,
                             @Param("endItem") int endItem);

    @Query(value = "SELECT * FROM STRAY WHERE ABDM_IDNTFY_NO = :ABDM_IDNTFY_NO",
            nativeQuery = true)
    Stray getDetailInfo(@Param("ABDM_IDNTFY_NO") String ABDM_IDNTFY_NO);
}
