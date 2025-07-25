package com.petthegarden.petthegarden.follow;

import com.petthegarden.petthegarden.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    @Modifying
    @Query(value = "INSERT INTO FOLLOW (followid, MemberID, PetID) values " +
            "(FOLLOW_SEQ.NEXTVAL, :fromMemberID,:toPetID)", nativeQuery = true)
    int follow(@Param("fromMemberID") Integer fromMemberID, @Param("toPetID") Integer toPetID);


    @Modifying
    @Query(value = "DELETE FROM FOLLOW  " +
            "WHERE MemberID = :fromMemberID AND PetID = :toPetID", nativeQuery = true)
    int unfollow(Integer fromMemberID, Integer toPetID);


    @Query(value = "select count(*) from Follow where PetID = :toPetID", nativeQuery = true)
    int getFollowers(@Param("toPetID")Integer toPetID);


    boolean existsByMember_IdAndPet_Id(Integer memberId, Integer petId);

}

