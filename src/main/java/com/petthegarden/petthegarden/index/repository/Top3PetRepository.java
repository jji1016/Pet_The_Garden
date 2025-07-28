package com.petthegarden.petthegarden.index.repository;

import com.petthegarden.petthegarden.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Top3PetRepository extends JpaRepository<Pet, Integer> {

        @Query(value = """
        SELECT 
            p.petid,
            p.profileImg AS profileImg,
            p.petName AS petName,
            p.species AS species,
            m.userName AS userName,
            COUNT(f.petID) AS followers
        FROM pet p
        JOIN follow f ON p.petID = f.petID
        JOIN member m ON p.memberID = m.memberID
        GROUP BY p.petid, p.profileImg, p.petName, p.species, m.userName
        ORDER BY followers DESC
        FETCH FIRST 3 ROWS ONLY
        """, nativeQuery = true)
        List<Object[]> findTop3PetFollowerRaw();



}
