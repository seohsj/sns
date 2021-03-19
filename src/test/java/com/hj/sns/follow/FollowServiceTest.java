package com.hj.sns.follow;

import com.hj.sns.user.User;
import com.hj.sns.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FollowServiceTest {
    @InjectMocks
    FollowService followService;

    @Mock
    FollowJpaRepository followJpaRepository;

    @Mock
    UserService userService;

//    @Test
//    void findFollowingsPaging() {
//        when(userService.findUserByName("userA")).thenReturn(new User("userA", "password"));
//
//    }

    @Test
    void findFollowings(){
//        List<Follow> followList = new ArrayList<>();
//        followList.add(new Follow(new User("userA", "password"), new User("userB", "password")));
//        followList.add(new Follow(new User("userA", "password"), new User("userC", "password")));
//
//       // when(followJpaRepository.findFollowByWho_Id(1L)).thenReturn(followList);
//        when(followJpaRepository.findFollowByWho(1L)).thenReturn(followList);
//        List<User> followings = followService.findFollowings(1L);
//        assertThat(followings.get(0).getUsername()).isEqualTo("userB");
//        assertThat(followings.get(1).getUsername()).isEqualTo("userC");
//        assertThat(followings.size()).isEqualTo(2);
//
//

    }
}
