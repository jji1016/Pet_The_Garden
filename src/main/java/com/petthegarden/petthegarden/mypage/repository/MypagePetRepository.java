package com.petthegarden.petthegarden.mypage.repository;

import com.petthegarden.petthegarden.entity.Member;
import com.petthegarden.petthegarden.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MypagePetRepository extends JpaRepository<Pet, Integer>{

}
