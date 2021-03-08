package com.hj.sns.follow.repository;

import com.hj.sns.follow.Follow;
import com.hj.sns.follow.FollowJpaRepository;
import com.hj.sns.user.model.User;
import com.hj.sns.user.repository.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class FollowRepositoryTest {

    @Autowired
    private FollowJpaRepository followJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;


    @Test
    @DisplayName("팔로잉한 사람들의 목록을 찾는다")
    void findFollowings() {
        User user1 = saveUser("seo", "fldlskeifk");
        User user2 = saveUser("kim", "abcd");
        User user3 = saveUser("lee", "fdsfeas");
        User user4 = saveUser("choi", "mkmkl");
        User user5 = saveUser("park", "basee");
        User user6 = saveUser("jo", "abesse");
        User user7 = saveUser("jung", "abcvasf");
        follow(user1, user2);
        follow(user1, user3);
        follow(user1, user4);
        follow(user1, user5);
        follow(user2, user3);
        follow(user2, user4);
        follow(user2, user6);
        follow(user3, user7);

        List<Follow> followings = followJpaRepository.findFollowings(user1.getId());
        List<Follow> followings2 = followJpaRepository.findFollowings(user2.getId());
        List<Follow> followings3 = followJpaRepository.findFollowings(user3.getId());
        assertThat(followings.size()).isEqualTo(4);
        assertTrue(followings.stream().allMatch(f -> (
                f.getWhom().equals(user2) || f.getWhom().equals(user3) || f.getWhom().equals(user4) || f.getWhom().equals(user5)
        )));

        assertThat(followings2.size()).isEqualTo(3);
        assertTrue(followings2.stream().allMatch(f -> (
                f.getWhom().equals(user3) || f.getWhom().equals(user4) || f.getWhom().equals(user6)
        )));

        assertThat(followings3.size()).isEqualTo(1);
        assertThat(followings3.get(0).getWhom()).isEqualTo(user7);

    }

    private void follow(User who, User whom) {
        followJpaRepository.save(new Follow(who, whom));
    }


    private User saveUser(String name, String password) {
        User user = new User(name, password);
        userJpaRepository.save(user);
        return user;

    }

}