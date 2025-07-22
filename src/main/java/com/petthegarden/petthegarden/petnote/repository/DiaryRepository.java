package com.petthegarden.petthegarden.petnote.repository;

import com.petthegarden.petthegarden.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary,Integer> {

    List<Diary> findByPet_Id(Integer petID);
}
