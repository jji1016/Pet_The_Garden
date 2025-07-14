package com.petthegarden.petthegarden.community.repository;

import com.petthegarden.petthegarden.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    Optional<Report> findByMemberIdAndRepIDAndType(Integer memberId, Integer repID, String type);
}