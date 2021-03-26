package com.hj.sns.follow;

import com.hj.sns.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowJpaRepository extends JpaRepository<Follow, Long> {

    @EntityGraph(attributePaths = {"whom"})
    List<Follow> findFollowByWho(User user);

    @EntityGraph(attributePaths = {"who"})
    List<Follow> findFollowByWhom(User user);

    Optional<Follow> findByWhoAndWhom(User who, User whom);

    @Query("select f from Follow f join fetch f.whom where f.who=:who")
    Slice<Follow> pagingFindByWho(@Param("who") User who, Pageable pageable);

    @Query("select f from Follow f join fetch f.who where f.whom=:whom")
    Slice<Follow> pagingFindByWhom(@Param("whom") User whom, Pageable pageable);
}
