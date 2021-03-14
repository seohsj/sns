package com.hj.sns.follow;

import com.hj.sns.user.model.User;
import com.hj.sns.user.repository.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class FollowJpaRepositoryTest {

    @Autowired
    private FollowJpaRepository followJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("팔로잉한 사람들의 목록을 찾는다")
    void findFollowByWho_Id() {
        HashMap<String, User> map = saveUsers();
        follow(map.get("user1"), map.get("user2"));
        follow(map.get("user1"), map.get("user3"));
        follow(map.get("user1"), map.get("user4"));
        follow(map.get("user1"), map.get("user5"));
        follow(map.get("user2"), map.get("user3"));
        follow(map.get("user2"), map.get("user4"));
        follow(map.get("user2"), map.get("user6"));
        follow(map.get("user3"), map.get("user7"));

        List<Follow> followings = followJpaRepository.findFollowByWho_Id(map.get("user1").getId());
        List<Follow> followings2 = followJpaRepository.findFollowByWho_Id(map.get("user2").getId());
        List<Follow> followings3 = followJpaRepository.findFollowByWho_Id(map.get("user3").getId());
        assertThat(followings.size()).isEqualTo(4);
        assertTrue(followings.stream().allMatch(f -> (
                f.getWhom().equals(map.get("user2")) || f.getWhom().equals(map.get("user3")) || f.getWhom().equals(map.get("user4")) || f.getWhom().equals(map.get("user5"))
        )));

        assertThat(followings2.size()).isEqualTo(3);
        assertTrue(followings2.stream().allMatch(f -> (
                f.getWhom().equals(map.get("user3")) || f.getWhom().equals(map.get("user4")) || f.getWhom().equals(map.get("user6"))
        )));

        assertThat(followings3.size()).isEqualTo(1);
        assertThat(followings3.get(0).getWhom()).isEqualTo(map.get("user7"));

    }


    @Test
    @DisplayName("follower와 following id로 Follow를 조회한다")
    void findByWho_IdAndWhom_Id() {

        HashMap<String, User> map = saveUsers();
        follow(map.get("user1"), map.get("user2"));
        follow(map.get("user2"), map.get("user1"));
        em.flush();
        em.clear();
        Optional<Follow> follow = followJpaRepository.findByWho_IdAndWhom_Id(map.get("user1").getId(), map.get("user2").getId());
        assertTrue(follow.isPresent());
        Optional<Follow> follow2 = followJpaRepository.findByWho_IdAndWhom_Id(map.get("user2").getId(), map.get("user1").getId());
        assertTrue(follow2.isPresent());
        Optional<Follow> follow3 = followJpaRepository.findByWho_IdAndWhom_Id(map.get("user3").getId(), map.get("user1").getId());
        assertTrue(follow3.isEmpty());
    }

    private HashMap<String, User> saveUsers() {
        HashMap<String, User> map = new HashMap<>();
        map.put("user1", saveUser("seo", "fldlskeifk"));
        map.put("user2", saveUser("kim", "fldlskeifk"));
        map.put("user3", saveUser("lee", "fldlskeifk"));
        map.put("user4", saveUser("choi", "fldlskeifk"));
        map.put("user5", saveUser("park", "fldlskeifk"));
        map.put("user6", saveUser("jo", "fldlskeifk"));
        map.put("user7", saveUser("jung", "fldlskeifk"));
        return map;

    }

    private void follow(User who, User whom) {
        followJpaRepository.save(new Follow(who, whom));
    }


    private User saveUser(String name, String password) {
        return userJpaRepository.save(new User(name, password));
    }

}