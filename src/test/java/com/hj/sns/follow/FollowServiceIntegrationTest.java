package com.hj.sns.follow;

import com.hj.sns.follow.dto.FollowerDto;
import com.hj.sns.follow.dto.FollowingDto;
import com.hj.sns.follow.exception.FollowAlreadyExistException;

import com.hj.sns.follow.exception.FollowNotFoundException;
import com.hj.sns.user.User;
import com.hj.sns.user.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FollowServiceIntegrationTest {

    @Autowired
    private FollowService followService;
    @Autowired
    private FollowJpaRepository followJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("userId의 팔로잉 목록을 조회한다.")
    void findFollowings() {
        User user1 = userJpaRepository.findByUsername("userA").orElse(null);
        User user2 = userJpaRepository.findByUsername("userB").orElse(null);


        em.flush();
        em.clear();

        List<User> followings = followService.findFollowings(user1);
        List<User> followings2 = followService.findFollowings(user2);


        assertThat(followings.size()).isEqualTo(4);
        assertTrue(followings.stream().allMatch(f -> (f.getUsername().equals("userB") || f.getUsername().equals("userC") || f.getUsername().equals("userD") || f.getUsername().equals("userE"))));
        assertThat(followings2.size()).isEqualTo(3);
        assertTrue(followings2.stream().allMatch(f -> (f.getUsername().equals("userA") || f.getUsername().equals("userC") || f.getUsername().equals("userD"))));


    }


    @Test
    @DisplayName("user의 팔로잉 목록을 조회 및 페이징 한다.")
    void findFollowingsPaging() {


        Slice<FollowingDto> followings = followService.findFollowingsPaging("userA",
                PageRequest.of(0, 20));

        assertThat(followings.getContent().size()).isEqualTo(4);
        assertTrue(followings.getContent().stream().allMatch(f -> (
                f.getUsername().equals("userB") ||
                        f.getUsername().equals("userC") ||
                        f.getUsername().equals("userD") ||
                        f.getUsername().equals("userE"))
        ));
        assertThat(followings.getNumber()).isEqualTo(0);
        assertFalse(followings.hasNext());


    }


    @Test
    @DisplayName("user의 팔로워 목록을 조회 및 페이징 한다")
    void findFollowersPaging() {

        Slice<FollowerDto> followers = followService.findFollowerPaging("userC",
                PageRequest.of(0, 20));
        assertThat(followers.getContent().size()).isEqualTo(2);
        assertTrue(followers.getContent().stream().allMatch(f -> (
                f.getUsername().equals("userA") ||
                        f.getUsername().equals("userB"))
        ));
        assertThat(followers.getNumber()).isEqualTo(0);
        assertFalse(followers.hasNext());


    }


    @Test
    @DisplayName("follow를 한다.")
    void follow() {
        User userA = userJpaRepository.findByUsername("userA").orElse(null);
        User userG = userJpaRepository.findByUsername("userG").orElse(null);
        followService.follow(userA.getUsername(), userG.getUsername());

        assertTrue(followJpaRepository.findByWhoAndWhom(userA, userG).isPresent());

    }

    @Test
    @DisplayName("이미 follow한 상태에서 follow 시 실패한다.")
    void followFail() {

        User userA = userJpaRepository.findByUsername("userA").orElse(null);
        User userB = userJpaRepository.findByUsername("userB").orElse(null);
        assertThrows(FollowAlreadyExistException.class,
                () -> followService.follow(userA.getUsername(), userB.getUsername())
        );
    }


    @Test
    @DisplayName("unfollow를 한다.")
    void unfollow() {
        User userA = userJpaRepository.findByUsername("userA").orElse(null);
        User userB = userJpaRepository.findByUsername("userB").orElse(null);
        followService.unfollow(userA.getUsername(), userB.getUsername());

        assertTrue(followJpaRepository.findByWhoAndWhom(userA, userB).isEmpty());

    }

    @Test
    @DisplayName("follow가 아닌 상태에서 unfollow를 하면 예외를 던진다.")
    void unfollowFail() {
        User userA = userJpaRepository.findByUsername("userA").orElse(null);
        User userG = userJpaRepository.findByUsername("userG").orElse(null);
        assertThrows(FollowNotFoundException.class,
                () -> followService.unfollow(userA.getUsername(), userG.getUsername())
        );

    }

//    private void follow(User who, User whom) {
//        followJpaRepository.save(new Follow(who, whom));
//    }
//

//    private User saveUser(String name, String password) {
//        User user = new User(name, password);
//        userJpaRepository.save(user);
//        return user;
//
//    }


}