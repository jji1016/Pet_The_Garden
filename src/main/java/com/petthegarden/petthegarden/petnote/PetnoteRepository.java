package com.petthegarden.petthegarden.petnote;

import com.petthegarden.petthegarden.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetnoteRepository extends JpaRepository<Diary,Integer> {

}
