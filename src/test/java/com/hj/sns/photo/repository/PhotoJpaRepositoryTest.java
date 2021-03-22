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
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PhotoJpaRepositoryTest {

    @Autowired
    PhotoJpaRepository photoJpaRepository;
    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("user가 업로드한 사진을 페이징으로 조회한다")
    void findPhotoByUser() {
        User user = userJpaRepository.save(new User("userA", "password"));
        photoJpaRepository.save(new Photo(user, "imagePath", "userA가 업로드한 사진"));
        photoJpaRepository.save(new Photo(user, "imagePath", "userA가 업로드한 사진"));
        photoJpaRepository.save(new Photo(user, "imagePath", "userA가 업로드한 사진"));


        Slice<Photo> photo = photoJpaRepository.findPhotoByUser(user, PageRequest.of(0, 20));
        assertThat(photo.getContent().size()).isEqualTo(3);


    }

    @Test
    @DisplayName("user, followings 의 사진을 페이징으로 조회 ")
    void findFeedByUser() {
        User user1 = userJpaRepository.save(new User("userA", "password"));
        User user2 = userJpaRepository.save(new User("userB", "password"));
        User user3 = userJpaRepository.save(new User("userC", "password"));
        photoJpaRepository.save(new Photo(user1, "imagePath", "userA가 업로드한 사진1"));
        photoJpaRepository.save(new Photo(user1, "imagePath", "userA가 업로드한 사진2"));
        photoJpaRepository.save(new Photo(user2, "imagePath", "userB 업로드한 사진"));
        photoJpaRepository.save(new Photo(user3, "imagePath", "userC가 업로드한 사진1"));
        photoJpaRepository.save(new Photo(user3, "imagePath", "userC가 #업로드한 사진2"));
        em.flush();
        em.clear();
        System.out.println("TEST -------------------------------");
        List<Long> ids = new ArrayList<>();
        ids.add(user1.getId());
        ids.add(user2.getId());
        ids.add(user3.getId());
        Slice<Photo> photos = photoJpaRepository.findFeedByUser(ids, PageRequest.of(0, 5, Sort.by((Sort.Direction.DESC), "user.username")));
        assertThat(photos.getContent().size()).isEqualTo(5);
        assertThat(photos.getContent().get(0).getUser().getUsername()).isEqualTo("userC");
        assertFalse(photos.hasNext());

        Slice<Photo> photos2 = photoJpaRepository.findFeedByUser(ids, PageRequest.of(0, 4, Sort.by((Sort.Direction.DESC), "user.username")));
        assertThat(photos2.getContent().size()).isEqualTo(4);
        assertTrue(photos2.hasNext());
        assertTrue(photos2.isFirst());


    }


}