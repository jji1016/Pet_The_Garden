package com.petthegarden.petthegarden.mypage.repository;

import com.petthegarden.petthegarden.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MypagePetRepository extends JpaRepository<Pet, Integer>{
    List<Pet> findByMemberId(Integer memberId);
}
