package com.hj.sns.photo.service;

import com.hj.sns.follow.Follow;
import com.hj.sns.follow.FollowJpaRepository;
import com.hj.sns.photo.model.Photo;
import com.hj.sns.photo.model.dto.PhotoDto;
import com.hj.sns.photo.repository.PhotoJpaRepository;
import com.hj.sns.tag.repository.TagJpaRepository;
import com.hj.sns.user.User;
import com.hj.sns.user.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class PhotoServiceIntegrationTest {

    @Autowired
    private PhotoService photoService;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private TagJpaRepository tagJpaRepository;
    @Autowired
    private PhotoJpaRepository photoJpaRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("photo를 저장한다.")
    void save() {
        User user = new User("userA", "password");
        userJpaRepository.save(user);


        Long photoId = photoService.save(user.getId(), "imagePath1", "content #tag1 #tag2");
        Long photoId2 = photoService.save(user.getId(), "imagePath1", "content #tag1 #tag2");
        Long photoId3 = photoService.save(user.getId(), "imagePath1", "content #tag1#tag3#tag2#tag3");

        em.flush();
        em.clear();

        Photo findPhoto = photoJpaRepository.findById(photoId).get();
        Photo findPhoto2 = photoJpaRepository.findById(photoId2).get();
        Photo findPhoto3 = photoJpaRepository.findById(photoId3).get();


        assertThat(findPhoto.getPhotoTags().size()).isEqualTo(2);
        assertThat(findPhoto.getUser().getId()).isEqualTo(user.getId());
        assertThat(findPhoto.getImagePath()).isEqualTo("imagePath1");
        assertTrue(findPhoto.getPhotoTags().stream()
                .allMatch(pt -> (
                        pt.getTag().getName().equals("tag1") || pt.getTag().getName().equals("tag2")
                )));

        assertThat(findPhoto2.getPhotoTags().size()).isEqualTo(2);
        assertThat(findPhoto2.getUser().getId()).isEqualTo(user.getId());
        assertThat(findPhoto2.getImagePath()).isEqualTo("imagePath1");
        assertTrue(findPhoto2.getPhotoTags().stream()
                .allMatch(pt -> (
                        pt.getTag().getName().equals("tag1") || pt.getTag().getName().equals("tag2")
                )));

        assertThat(findPhoto3.getPhotoTags().size()).isEqualTo(4);
        assertThat(findPhoto3.getUser().getId()).isEqualTo(user.getId());
        assertThat(findPhoto3.getImagePath()).isEqualTo("imagePath1");
        assertTrue(findPhoto3.getPhotoTags().stream()
                .allMatch(pt -> (
                        pt.getTag().getName().equals("tag1") || pt.getTag().getName().equals("tag2")|| pt.getTag().getName().equals("tag3")
                )));

        assertTrue(tagJpaRepository.findByName("tag1").isPresent());
        assertTrue(tagJpaRepository.findByName("tag2").isPresent());
        assertTrue(tagJpaRepository.findByName("tag3").isPresent());

    }


    @Test
    @DisplayName("user가 업로드한 사진을 페이징으로 조회한다")
    void findPhotoByUser() {
        User user = new User("userA", "password");
        userJpaRepository.save(user);
        photoService.save(user.getId(), "imagePath", "content #content");
        photoService.save(user.getId(), "imagePath", "#hi#photo");
        photoService.save(user.getId(), "imagePath", "#content");
        em.flush();
        em.clear();
        Slice<PhotoDto> photoByUser = photoService.findPhotoByUser(user.getUsername(), PageRequest.of(0, 20));

        assertThat(photoByUser.getContent().size()).isEqualTo(3);
        assertFalse(photoByUser.hasNext());
        assertTrue(photoByUser.isFirst());
        for (PhotoDto p : photoByUser.getContent()) {
            assertTrue(p.getTags().stream().allMatch(t -> (t.getTagName().equals("content") || t.getTagName().equals("hi") || t.getTagName().equals("photo"))));

        }
    }
}