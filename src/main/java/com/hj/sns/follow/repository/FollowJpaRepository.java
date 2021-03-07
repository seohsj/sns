package com.hj.sns.follow.repository;

import com.hj.sns.follow.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowJpaRepository extends JpaRepository<Follow, Long> {
}
