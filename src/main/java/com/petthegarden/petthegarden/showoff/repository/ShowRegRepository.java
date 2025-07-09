package com.petthegarden.petthegarden.showoff.repository;

import com.petthegarden.petthegarden.entity.ShowOff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRegRepository extends JpaRepository<ShowOff, Integer> {
}
