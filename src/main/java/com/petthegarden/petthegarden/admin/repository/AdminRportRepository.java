package com.petthegarden.petthegarden.admin.repository;

import com.petthegarden.petthegarden.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRportRepository extends JpaRepository<Report,Integer> {
}
