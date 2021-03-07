package com.hj.sns.follow.repository;

import com.hj.sns.follow.Follow;
import com.hj.sns.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FollowRepository {
    private final EntityManager em;
    public List<User> findFollowings(Long userId){

        List<Follow> follows = em.createQuery("select f from Follow f join fetch f.who w join fetch f.whom wh where w.id=:userId", Follow.class)
                .setParameter("userId",userId)
                .getResultList();

        return follows.stream().map(f->f.getWhom()).collect(Collectors.toList());
    }
}
