package com.hj.sns.photo.service;

import com.hj.sns.follow.FollowService;
import com.hj.sns.photo.repository.PhotoJpaRepository;
import com.hj.sns.photo.repository.PhotoRepository;
import com.hj.sns.tag.service.TagService;
import com.hj.sns.user.model.User;
import com.hj.sns.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

/*TODO: 완성하기*/

@ExtendWith(MockitoExtension.class)
public class PhotoServiceTest {
    @InjectMocks
    PhotoService photoService;

    @Mock
    PhotoJpaRepository photoJpaRepository;
    @Mock
    FollowService followService;
    @Mock
    PhotoRepository photoQueryRepository;
    @Mock
    UserService userService;
    @Mock
    TagService tagService;

//    @Test
//    @DisplayName("photo를 저장한다.")
//    void save() {
//        when(userService.findUserById(1L)).thenReturn(new User("userA","password"));
//
//
//    }
//
//
//    @Test
//    @DisplayName("following하고 있는 사람의 사진들을 모두 조회한다")
//    void findAllPhotosOfFollowing() {
//
//    }

}
