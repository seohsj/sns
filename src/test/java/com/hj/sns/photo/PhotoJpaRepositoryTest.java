package com.hj.sns.photo;

import com.hj.sns.photo.PhotoJpaRepository;
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

        User user = userJpaRepository.findByUsername("userA").orElse(null);
        Slice<Photo> photo = photoJpaRepository.findPhotoByUser(user, PageRequest.of(0, 20));
        assertThat(photo.getContent().size()).isEqualTo(3);


    }

    @Test
    @DisplayName("user, followings 의 사진을 페이징으로 조회 ")
    void findFeedByUser() {

        User userA = userJpaRepository.findByUsername("userA").orElseGet(null);
        User userB = userJpaRepository.findByUsername("userB").orElseGet(null);
        User userC = userJpaRepository.findByUsername("userC").orElseGet(null);

        em.flush();
        em.clear();
        System.out.println("TEST -------------------------------");
        List<Long> ids = new ArrayList<>();
        ids.add(userA.getId());
        ids.add(userB.getId());
        ids.add(userC.getId());
        Slice<Photo> photos = photoJpaRepository.findFeedByUser(ids, PageRequest.of(0, 20, Sort.by((Sort.Direction.DESC), "user.username")));
        assertThat(photos.getContent().size()).isEqualTo(6);
        assertThat(photos.getContent().get(0).getUser().getUsername()).isEqualTo("userC");
        assertFalse(photos.hasNext());

        Slice<Photo> photos2 = photoJpaRepository.findFeedByUser(ids, PageRequest.of(0, 4, Sort.by((Sort.Direction.DESC), "user.username")));
        assertThat(photos2.getContent().size()).isEqualTo(4);
        assertTrue(photos2.hasNext());
        assertTrue(photos2.isFirst());


    }


}