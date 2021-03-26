package com.hj.sns.photo.service;

import com.hj.sns.comment.CommentJpaRepository;
import com.hj.sns.comment.CommentService;
import com.hj.sns.follow.FollowService;
import com.hj.sns.photo.exception.PhotoNotFoundException;
import com.hj.sns.photo.model.Photo;
import com.hj.sns.photo.model.PhotoTag;
import com.hj.sns.photo.model.dto.PhotoDto;
import com.hj.sns.photo.model.dto.PhotoFeedDto;
import com.hj.sns.photo.repository.PhotoJpaRepository;
import com.hj.sns.tag.repository.TagJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PhotoServiceIntegrationTest {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private TagJpaRepository tagJpaRepository;
    @Autowired
    private PhotoJpaRepository photoJpaRepository;
    @Autowired
    private FollowService followService;
    @Autowired
    private CommentService commentService;
@Autowired private CommentJpaRepository commentJpaRepository;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("photo를 저장한다.")
    void save() {

        Long photoId = photoService.save(1L, "imagePath1", "content #tag1 #tag2");
        Long photoId2 = photoService.save(1L, "imagePath1", "content #tag1 #tag2");
        Long photoId3 = photoService.save(1L, "imagePath1", "content #tag1#tag3#tag2#tag3");

        em.flush();
        em.clear();

        Photo findPhoto = photoJpaRepository.findById(photoId).get();
        Photo findPhoto2 = photoJpaRepository.findById(photoId2).get();
        Photo findPhoto3 = photoJpaRepository.findById(photoId3).get();


        assertThat(findPhoto.getPhotoTags().size()).isEqualTo(2);
        assertThat(findPhoto.getUser().getId()).isEqualTo(1L);
        assertThat(findPhoto.getImagePath()).isEqualTo("imagePath1");
        assertTrue(findPhoto.getPhotoTags().stream()
                .allMatch(pt -> (
                        pt.getTag().getName().equals("tag1") || pt.getTag().getName().equals("tag2")
                )));

        assertThat(findPhoto2.getPhotoTags().size()).isEqualTo(2);
        assertThat(findPhoto2.getUser().getId()).isEqualTo(1L);
        assertThat(findPhoto2.getImagePath()).isEqualTo("imagePath1");
        assertTrue(findPhoto2.getPhotoTags().stream()
                .allMatch(pt -> (
                        pt.getTag().getName().equals("tag1") || pt.getTag().getName().equals("tag2")
                )));

        assertThat(findPhoto3.getPhotoTags().size()).isEqualTo(3);
        assertThat(findPhoto3.getUser().getId()).isEqualTo(1L);
        assertThat(findPhoto3.getImagePath()).isEqualTo("imagePath1");
        assertTrue(findPhoto3.getPhotoTags().stream()
                .allMatch(pt -> (
                        pt.getTag().getName().equals("tag1") || pt.getTag().getName().equals("tag2") || pt.getTag().getName().equals("tag3")
                )));

        assertTrue(tagJpaRepository.findByName("tag1").isPresent());
        assertTrue(tagJpaRepository.findByName("tag2").isPresent());
        assertTrue(tagJpaRepository.findByName("tag3").isPresent());

    }

    @Test
    @DisplayName("photo를 수정한다.")
    void updatePhoto() {

        photoService.updatePhoto(1L, null, "#update#content");

        em.flush();
        em.clear();
        System.out.println("---------------------------2");
        Photo photo = photoService.findPhotoById(1L);
        assertThat(photo.getImagePath()).isEqualTo("imagePath");
        assertTrue(photo.getPhotoTags().stream().allMatch(pt ->
                (pt.getTag().getName().equals("content") || pt.getTag().getName().equals("update")))
        );
        assertThat(photo.getPhotoTags().size()).isEqualTo(2);
        em.flush();
        em.clear();
        photoService.updatePhoto(1L, "newImagePath", null);
        em.flush();
        em.clear();
        Photo photo2 = photoService.findPhotoById(1L);
        assertThat(photo2.getImagePath()).isEqualTo("newImagePath");
        assertTrue(photo2.getPhotoTags().stream().allMatch(pt ->
                (pt.getTag().getName().equals("content") || pt.getTag().getName().equals("update")))
        );
        assertThat(photo2.getPhotoTags().size()).isEqualTo(2);
        em.flush();
        em.clear();
        photoService.updatePhoto(1L, "NewNewImagePath", "newContent");
        em.flush();
        em.clear();
        Photo photo3 = photoService.findPhotoById(1L);
        assertThat(photo3.getImagePath()).isEqualTo("NewNewImagePath");
        assertThat(photo3.getPhotoTags().size()).isEqualTo(0);

    }


    @Test
    @DisplayName("user가 업로드한 사진을 페이징으로 조회한다")
    void findPhotoByUser() {

        Slice<PhotoDto> photoByUser = photoService.findPhotoByUser("userA", PageRequest.of(0, 20));

        assertThat(photoByUser.getContent().size()).isEqualTo(3);
        assertFalse(photoByUser.hasNext());
        assertTrue(photoByUser.isFirst());
        for (PhotoDto p : photoByUser.getContent()) {
            assertTrue(p.getTags().stream()
                    .allMatch(t -> (
                            t.getTagName().equals("tagA") ||
                                    t.getTagName().equals("tagB") ||
                                    t.getTagName().equals("tagC")||
                                    t.getTagName().equals("tagD")
                    ))
            );
            int size = p.getComments().size();
            assertTrue(size==3||size==0||size==1);

        }

    }

    @Test
    @DisplayName("user의 피드를 조회한다")
    void getFeedByUser() {

        Slice<PhotoFeedDto> photos = photoService.getUserFeed("userA", PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "id")));

        assertThat(photos.getContent().size()).isEqualTo(6);
        assertThat(photos.getContent().get(0).getUsername()).isEqualTo("userC");

        followService.unfollow("userA", "userB");
        em.flush();
        em.clear();
        Slice<PhotoFeedDto> photos3 = photoService.getUserFeed("userA", PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "id")));
        assertThat(photos3.getContent().size()).isEqualTo(4);

        followService.unfollow("userA", "userC");
        em.flush();
        em.clear();
        Slice<PhotoFeedDto> photos4 = photoService.getUserFeed("userA", PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "id")));
        assertThat(photos4.getContent().size()).isEqualTo(3);

    }

    @Test
    @DisplayName("photo를 삭제한다.")
    void deletePhoto() {

        photoService.deletePhoto(1L);
        assertThrows(PhotoNotFoundException.class, ()->photoService.findPhotoById(1L));
        assertTrue(commentJpaRepository.findById(1L).isEmpty());
        assertTrue(commentJpaRepository.findById(4L).isEmpty());
        assertTrue(commentJpaRepository.findById(5L).isEmpty());
        assertThat(em.find(PhotoTag.class,1L)).isNull();
    }
}