package com.petthegarden.petthegarden.follow;

import com.petthegarden.petthegarden.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    @Modifying
    @Query(value = "INSERT INTO FOLLOW (followid, fromMemberID, toPetID) values " +
            "(FOLLOW_SEQ.NEXTVAL, :fromMemberID,:toPetID)", nativeQuery = true)
    int follow(@Param("fromMemberID") String fromMemberID, @Param("toPetID") String toPetID);



    @Modifying
    @Query(value = "DELETE FROM FOLLOW  " +
            "WHERE fromMemberID = :fromMemberID AND toPetID = :toPetID", nativeQuery = true)
    int unfollow(String fromMemberID, String toPetID);
}