package com.petthegarden.petthegarden.petnote.repository;

import com.petthegarden.petthegarden.entity.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary,Integer> {

    List<Diary> findByPet_Id(Integer petID);

    List<Diary> findByPet_IdOrderByRegDateDesc(Integer petID);

    Page<Diary> findByPet_Id(Integer petID, Pageable pageable);

}
