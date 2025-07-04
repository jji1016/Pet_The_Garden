package com.petthegarden.petthegarden.stray;

import com.petthegarden.petthegarden.entity.Stray;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StrayRepository extends JpaRepository<Stray, Integer> {
}
