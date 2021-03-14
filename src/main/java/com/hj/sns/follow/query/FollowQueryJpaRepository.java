package com.hj.sns.follow.query;

import com.hj.sns.follow.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowQueryJpaRepository extends JpaRepository<Follow, Long> {

    @Query(value = "select new com.hj.sns.follow.query.FollowingQueryDto(wh.username) from Follow f " +
            "join f.whom wh where f.who.id=:whoId"
            , countQuery = "select count(f.id) from Follow f where f.who.id=:whoId")
    Page<FollowingQueryDto> pagingFindByWho(@Param("whoId") Long whoId, Pageable pageable);

}
