package com.hj.sns.photo.repository;

import com.hj.sns.photo.model.Photo;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PhotoJpaRepositoryTest {

    @Autowired
    PhotoJpaRepository photoJpaRepository;
    @Autowired
    UserJpaRepository userJpaRepository;

    @Test
    @DisplayName("user가 업로드한 사진을 페이징으로 조회한다")
    void findPhotoByUser() {
        User user = userJpaRepository.save(new User("userA", "password"));
        photoJpaRepository.save(new Photo(user, "imagePath", "userA가 업로드한 사진"));
        photoJpaRepository.save(new Photo(user, "imagePath", "userA가 업로드한 사진"));
        photoJpaRepository.save(new Photo(user, "imagePath", "userA가 업로드한 사진"));


        Slice<Photo> photo = photoJpaRepository.findPhotoByUser(user, PageRequest.of(0,20));

        assertThat(photo.getContent().size()).isEqualTo(3);
        for (Photo p : photo.getContent()) {
            System.out.println(p.getContent());
        }

    }


}