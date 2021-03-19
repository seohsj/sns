package com.hj.sns.photo.service;

import com.hj.sns.follow.Follow;
import com.hj.sns.follow.FollowJpaRepository;
import com.hj.sns.photo.model.Photo;
import com.hj.sns.photo.repository.PhotoJpaRepository;
import com.hj.sns.user.User;
import com.hj.sns.user.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class PhotoServiceIntegrationTest {

    @Autowired
    private PhotoService photoService;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private FollowJpaRepository followJpaRepository;
    @Autowired
    private PhotoJpaRepository photoJpaRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("photo를 저장한다.")
    void save() {
        User user = new User("userA", "password");
        userJpaRepository.save(user);
        String tag1 = "tag1";
        String tag2 = "tag2";
        String content = "content1";
        content = content + "#" + tag1 + "#" + tag2;
        String imagePath = "imagePath1";

        Long photoId = photoService.save(user.getId(), imagePath, content);
        Photo findPhoto = photoJpaRepository.findById(photoId).get();

        assertThat(findPhoto.getPhotoTags().size()).isEqualTo(2);
        assertThat(findPhoto.getUser().getId()).isEqualTo(user.getId());
        assertThat(findPhoto.getImagePath()).isEqualTo(imagePath);
        assertTrue(findPhoto.getPhotoTags().stream()
                .allMatch(pt -> (
                        pt.getTag().getName().equals(tag1) || pt.getTag().getName().equals(tag2)
                )));
    }

}