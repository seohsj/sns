package com.hj.sns.follow;

import com.hj.sns.user.User;
import com.hj.sns.user.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
    @DisplayName("팔로잉한 사람들의 목록을 찾는다")
    void findFollowByWho() {
        HashMap<String, User> map = saveUsers(7);
        follow(map.get("user1"), map.get("user2"));
        follow(map.get("user1"), map.get("user3"));
        follow(map.get("user1"), map.get("user4"));
        follow(map.get("user1"), map.get("user5"));
        follow(map.get("user2"), map.get("user3"));
        follow(map.get("user2"), map.get("user4"));
        follow(map.get("user2"), map.get("user6"));
        follow(map.get("user3"), map.get("user7"));

        List<Follow> followings = followJpaRepository.findFollowByWho(map.get("user1"));
        List<Follow> followings2 = followJpaRepository.findFollowByWho(map.get("user2"));
        List<Follow> followings3 = followJpaRepository.findFollowByWho(map.get("user3"));
        assertThat(followings.size()).isEqualTo(4);
        assertTrue(followings.stream().allMatch(f -> (
                f.getWhom().equals(map.get("user2")) ||
                        f.getWhom().equals(map.get("user3")) ||
                        f.getWhom().equals(map.get("user4")) ||
                        f.getWhom().equals(map.get("user5"))
        )));

        assertThat(followings2.size()).isEqualTo(3);
        assertTrue(followings2.stream().allMatch(f -> (
                f.getWhom().equals(map.get("user3")) ||
                        f.getWhom().equals(map.get("user4")) ||
                        f.getWhom().equals(map.get("user6"))
        )));

        assertThat(followings3.size()).isEqualTo(1);
        assertThat(followings3.get(0).getWhom()).isEqualTo(map.get("user7"));

    }

    @Test
    @DisplayName("userid의 팔로우 목록을 조회 및 페이징 한다.")
    void findFollowPaging() {
        HashMap<String, User> map = saveUsers(5);
        follow(map.get("user2"), map.get("user1"));
        follow(map.get("user3"), map.get("user1"));
        follow(map.get("user4"), map.get("user1"));
        follow(map.get("user5"), map.get("user1"));
        follow(map.get("user1"), map.get("user5"));


        Slice<Follow> followers = followJpaRepository.pagingFindByWhom(map.get("user1"),
                PageRequest.of(1, 2));
        assertThat(followers.getContent().size()).isEqualTo(2);
        assertTrue(followers.getContent().stream().allMatch(f -> (
                f.getWho().getUsername().equals(map.get("user4").getUsername()) ||
                        f.getWho().getUsername().equals(map.get("user5").getUsername()))
        ));
        assertThat(followers.getNumber()).isEqualTo(1);
        assertFalse(followers.hasNext());
        assertTrue(followers.hasPrevious());
        assertTrue(followers.isLast());

    }

    @Test
    @DisplayName("userid의 팔로잉 목록을 조회 및 페이징 한다.")
    void findFollowingsPaging() {
        HashMap<String, User> map = saveUsers(5);

        follow(map.get("user1"), map.get("user2"));
        follow(map.get("user1"), map.get("user3"));
        follow(map.get("user1"), map.get("user4"));
        follow(map.get("user1"), map.get("user5"));


        Slice<Follow> followings = followJpaRepository.pagingFindByWho(map.get("user1"),
                PageRequest.of(1, 2));


        assertThat(followings.getContent().size()).isEqualTo(2);
        assertTrue(followings.getContent().stream().allMatch(f -> (
                f.getWhom().getUsername().equals(map.get("user4").getUsername()) ||
                        f.getWhom().getUsername().equals(map.get("user5").getUsername()))
        ));
        assertThat(followings.getNumber()).isEqualTo(1);
        assertFalse(followings.hasNext());
        assertTrue(followings.hasPrevious());
        assertTrue(followings.isLast());

    }

    @Test
    @DisplayName("follower와 following id로 Follow를 조회한다")
    void findByWhoAndWhom() {

        HashMap<String, User> map = saveUsers(4);
        follow(map.get("user1"), map.get("user2"));
        follow(map.get("user2"), map.get("user1"));
        follow(map.get("user1"), map.get("user4"));
        //       em.flush();
        //     em.clear();

        Optional<Follow> follow = followJpaRepository.findByWhoAndWhom(map.get("user1"), map.get("user2"));
        assertTrue(follow.isPresent());
        Optional<Follow> follow2 = followJpaRepository.findByWhoAndWhom(map.get("user2"), map.get("user1"));
        assertTrue(follow2.isPresent());
        Optional<Follow> follow3 = followJpaRepository.findByWhoAndWhom(map.get("user3"), map.get("user1"));
        assertTrue(follow3.isEmpty());
        Optional<Follow> follow4 = followJpaRepository.findByWhoAndWhom(map.get("user1"), map.get("user4"));
        assertTrue(follow4.isPresent());

    }


    private HashMap<String, User> saveUsers(int userNum) {
        HashMap<String, User> map = new HashMap<>();
        for (char i = '1'; i <= (char) (userNum + '0'); i++) {
            String name = "user" + i;
            map.put(name, saveUser(name, "password"));
        }
        return map;

    }

    private void follow(User who, User whom) {
        followJpaRepository.save(new Follow(who, whom));
    }


    private User saveUser(String name, String password) {
        return userJpaRepository.save(new User(name, password));
    }

}