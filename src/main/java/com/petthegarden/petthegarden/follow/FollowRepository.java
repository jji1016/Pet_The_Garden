package com.petthegarden.petthegarden.follow;

import com.petthegarden.petthegarden.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    @Modifying
    @Query(value = "INSERT INTO FOLLOW (id, MemberID, PetID) values " +
            "(FOLLOW_SEQ.NEXTVAL, :MemberID,:PetID)", nativeQuery = true)
    int follow(@Param("MemberID") String MemberID, @Param("PetID") String PetID);
}
