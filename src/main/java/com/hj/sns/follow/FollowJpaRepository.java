package com.hj.sns.follow;

import com.hj.sns.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowJpaRepository extends JpaRepository<Follow, Long> {
        @Query("select f from Follow f join fetch f.who w join fetch f.whom wh where w.id=:userId")
        List<Follow> findFollowings(@Param("userId") Long userId);

        Optional<Follow> findByWhoAndWhom(User who, User whom);
}
