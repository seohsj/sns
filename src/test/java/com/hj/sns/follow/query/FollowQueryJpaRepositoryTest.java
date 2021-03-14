package com.hj.sns.follow.query;

import com.hj.sns.follow.Follow;
import com.hj.sns.follow.FollowJpaRepository;
import com.hj.sns.user.model.User;
import com.hj.sns.user.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FollowQueryJpaRepositoryTest {

    @Autowired
    private FollowQueryJpaRepository followRepository;
    @Autowired
    private FollowJpaRepository followJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void pagingFindByWho() {
        HashMap<String, User> map = saveUsers();
        follow(map.get("user1"), map.get("user2"));
        follow(map.get("user1"), map.get("user3"));
        follow(map.get("user1"), map.get("user4"));
        follow(map.get("user1"), map.get("user5"));
        follow(map.get("user1"), map.get("user6"));

        Page<FollowingQueryDto> following=followRepository.pagingFindByWho(map.get("user1").getId(),PageRequest.of(0,3));
        assertThat(following.getContent().size()).isEqualTo(3);
        assertTrue(following.isFirst());
        assertTrue(following.hasNext());
        assertThat(following.getNumber()).isEqualTo(0);

        Page<FollowingQueryDto> following2=followRepository.pagingFindByWho(map.get("user1").getId(),PageRequest.of(1,3));
        assertThat(following2.getContent().size()).isEqualTo(2);
        assertFalse(following2.hasNext());
        assertThat(following2.getNumber()).isEqualTo(1);
        assertTrue(following2.isLast());
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