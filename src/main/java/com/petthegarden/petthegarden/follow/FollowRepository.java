package com.petthegarden.petthegarden.follow;

import com.petthegarden.petthegarden.entity.Follow;
import com.petthegarden.petthegarden.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    @Query("SELECT f.pet FROM Follow f WHERE f.member.id = :memberId")
    List<Pet> findFollowByFollowMemberId(@Param("memberId") Integer memberId);

}

