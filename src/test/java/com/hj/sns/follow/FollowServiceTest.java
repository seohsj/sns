package com.hj.sns.follow;

import com.hj.sns.follow.exception.FollowAlreadyExistException;
import com.hj.sns.user.model.User;
import com.hj.sns.user.repository.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FollowServiceTest {

    @Autowired
    private FollowService followService;
    @Autowired
    private FollowJpaRepository followJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;


    @Test
    @DisplayName("userId의 팔로잉 목록을 조회한다.")
    void findFollowings() {
        User user1 = saveUser("seo", "fldlskeifk");
        User user2 = saveUser("kim", "abcd");
        User user3 = saveUser("lee", "fdsfeas");
        User user4 = saveUser("choi", "mkmkl");
        User user5 = saveUser("park", "basee");
        follow(user1, user2);
        follow(user1, user3);
        follow(user1, user4);
        follow(user1, user5);
        follow(user2, user3);
        follow(user2, user4);
        follow(user2, user1);


        List<User> followings = followService.findFollowings(user1.getId());
        List<User> followings2 = followService.findFollowings(user2.getId());


        assertThat(followings.size()).isEqualTo(4);
        assertTrue(followings.stream().allMatch(f -> (f.equals(user2) || f.equals(user3) || f.equals(user4) || f.equals(user5))));
        assertThat(followings2.size()).isEqualTo(3);
        assertTrue(followings2.stream().allMatch(f -> (f.equals(user3) || f.equals(user4) || f.equals(user1))));


    }


    @Test
    @DisplayName("follow를 한다.")
    void follow() {
        User user1 = saveUser("seo", "fldlskeifk");
        User user2 = saveUser("kim", "abcd");

        followService.follow(user1.getId(), user2.getId());

        assertTrue(followJpaRepository.findByWho_IdAndWhom_Id(user1.getId(), user2.getId()).isPresent());

    }

    @Test
    @DisplayName("이미 follow한 상태에서 follow 시 실패한다.")
    void followFail() {
        User user1 = saveUser("seo", "fldlskeifk");
        User user2 = saveUser("kim", "abcd");

        followService.follow(user1.getId(), user2.getId());
        assertThrows(FollowAlreadyExistException.class,
                () -> followService.follow(user1.getId(), user2.getId())
        );
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