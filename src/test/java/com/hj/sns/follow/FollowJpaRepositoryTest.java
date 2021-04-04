package com.hj.sns.follow;

import com.hj.sns.user.User;
import com.hj.sns.user.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class FollowJpaRepositoryTest {

    @Autowired
    private FollowJpaRepository followJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;


    //@Autowired
    //EntityManager em;

    @Test
    @DisplayName("팔로잉 목록을 찾는다")
    void findFollowByWho() {
        User user1 = userJpaRepository.findByUsername("userA").orElse(null);
        User user2 = userJpaRepository.findByUsername("userB").orElse(null);
        User user3 = userJpaRepository.findByUsername("userC").orElse(null);
        User user4 = userJpaRepository.findByUsername("userD").orElse(null);
        User user5 = userJpaRepository.findByUsername("userE").orElse(null);


        List<Follow> followings = followJpaRepository.findFollowByWho(user1);
        List<Follow> followings2 = followJpaRepository.findFollowByWho(user2);
        List<Follow> followings3 = followJpaRepository.findFollowByWho(user3);
        assertThat(followings.size()).isEqualTo(4);
        assertTrue(followings.stream().allMatch(f -> (
                f.getWhom().equals(user2) ||
                        f.getWhom().equals(user3) ||
                        f.getWhom().equals(user4) ||
                        f.getWhom().equals(user5)
        )));

        assertThat(followings2.size()).isEqualTo(3);
        assertTrue(followings2.stream().allMatch(f -> (
                f.getWhom().equals(user1) ||
                        f.getWhom().equals(user3) ||
                        f.getWhom().equals(user4)
        )));

        assertThat(followings3.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("팔로워 목록을 찾는다")
    void findFollowByWhom() {
        User userA = userJpaRepository.findByUsername("userA").orElse(null);
        User userB = userJpaRepository.findByUsername("userB").orElse(null);
        User userC = userJpaRepository.findByUsername("userC").orElse(null);
        User userG = userJpaRepository.findByUsername("userG").orElse(null);

        List<Follow> followers = followJpaRepository.findFollowByWhom(userA);
        List<Follow> followers2 = followJpaRepository.findFollowByWhom(userC);
        List<Follow> followers3 = followJpaRepository.findFollowByWhom(userG);
        assertThat(followers.size()).isEqualTo(1);
        assertTrue(followers.get(0).getWho().equals(userB));

        assertThat(followers2.size()).isEqualTo(2);
        assertTrue(followers2.stream().allMatch(f -> (
                f.getWho().equals(userA) ||
                        f.getWho().equals(userB)
        )));

        assertThat(followers3.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("userid의 팔로워 목록을 조회 및 페이징 한다.")
    void findFollowPaging() {
        User userA = userJpaRepository.findByUsername("userA").orElse(null);
        User userB = userJpaRepository.findByUsername("userB").orElse(null);

        Slice<Follow> followers = followJpaRepository.pagingFindByWhom(userA,
                PageRequest.of(0, 2));
        assertThat(followers.getContent().size()).isEqualTo(1);
        assertTrue(followers.getContent().get(0).getWho().equals(userB));
        assertThat(followers.getNumber()).isEqualTo(0);
        assertFalse(followers.hasPrevious());
        assertFalse(followers.hasNext());
        assertTrue(followers.isLast());

    }

    @Test
    @DisplayName("userid의 팔로잉 목록을 조회 및 페이징 한다.")
    void findFollowingsPaging() {
        User userA = userJpaRepository.findByUsername("userA").orElse(null);
        User userD = userJpaRepository.findByUsername("userD").orElse(null);
        User userE = userJpaRepository.findByUsername("userE").orElse(null);


        Slice<Follow> followings = followJpaRepository.pagingFindByWho(userA,
                PageRequest.of(1, 2, Sort.by(Sort.Direction.ASC, "id")));


        assertThat(followings.getContent().size()).isEqualTo(2);
        assertTrue(followings.getContent().stream().allMatch(f -> (
                f.getWhom().getUsername().equals(userD.getUsername())) ||
                f.getWhom().getUsername().equals(userE.getUsername()))
        );
        assertThat(followings.getNumber()).isEqualTo(1);
        assertFalse(followings.hasNext());
        assertTrue(followings.hasPrevious());
        assertTrue(followings.isLast());

    }

    @Test
    @DisplayName("follower와 following id로 Follow를 조회한다")
    void findByWhoAndWhom() {

        User userA = userJpaRepository.findByUsername("userA").orElse(null);
        User userB = userJpaRepository.findByUsername("userB").orElse(null);
        User userC = userJpaRepository.findByUsername("userC").orElse(null);
        User userD = userJpaRepository.findByUsername("userD").orElse(null);


        Optional<Follow> follow = followJpaRepository.findByWhoAndWhom(userA, userB);
        assertTrue(follow.isPresent());
        Optional<Follow> follow2 = followJpaRepository.findByWhoAndWhom(userB, userA);
        assertTrue(follow2.isPresent());
        Optional<Follow> follow3 = followJpaRepository.findByWhoAndWhom(userC, userA);
        assertTrue(follow3.isEmpty());
        Optional<Follow> follow4 = followJpaRepository.findByWhoAndWhom(userA, userD);
        assertTrue(follow4.isPresent());

    }



}