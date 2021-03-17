package com.hj.sns.follow;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowJpaRepository extends JpaRepository<Follow, Long> {
    @Query("select f from Follow f join fetch f.whom where f.who.id=:userId")
    List<Follow> findFollowByWho_Id(@Param("userId") Long userId);

    @Query("select f from Follow f where f.who.id=:whoId and f.whom.id=:whomId")
    Optional<Follow> findByWho_IdAndWhom_Id(@Param("whoId") Long whoId, @Param("whomId") Long whomId);

    @Query("select f from Follow f join fetch f.whom where f.who.id=:whoId")
    Slice<Follow> pagingFindByWho(@Param("whoId") Long whoId, Pageable pageable);

    @Query("select f from Follow f join fetch f.who where f.whom.id=:whomId")
    Slice<Follow> pagingFindByWhom(@Param("whomId") Long whomId, Pageable pageable);
}
