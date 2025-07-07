package com.petthegarden.petthegarden.petnote.repository;

import com.petthegarden.petthegarden.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetnoteRepository extends JpaRepository<Diary,Integer> {

    Optional<Diary> findFirstByMemberId(Integer memberId);

}
