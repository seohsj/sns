package com.hj.sns.photo.service;

import com.hj.sns.follow.Follow;
import com.hj.sns.follow.FollowJpaRepository;
import com.hj.sns.photo.model.Photo;
import com.hj.sns.photo.repository.PhotoJpaRepository;
import com.hj.sns.user.model.User;
import com.hj.sns.user.repository.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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


    @Test
    @DisplayName("following하고 있는 사람의 사진들을 모두 조회한다")
    void findAllPhotosOfFollowing() {
        User user1 = saveUser("seo", "fldlskeifk");
        photoService.save(user1.getId(), "imagepath1", "#스프링");
        photoService.save(user1.getId(), "imagepath2", "contentcontent");
        photoService.save(user1.getId(), "imagepath3", "#tag#test");

        User user2 = saveUser("kim", "abcd");
        photoService.save(user2.getId(), "imagepath4", "#jpa#spring");
        photoService.save(user2.getId(), "imagepath5", "abcdefg#aaaaa");

        User user3 = saveUser("lee", "fdsfeas");
        photoService.save(user3.getId(), "imagepath6", "#태그");
        photoService.save(user3.getId(), "imagepath7", "테스트테스트#테스트 테스트");
        photoService.save(user3.getId(), "imagepath8", "학교#맛집");


        User user4 = saveUser("choi", "mkmkl");
        photoService.save(user4.getId(), "imagepath9", "#photo");


        follow(user1, user2);
        follow(user1, user3);
        follow(user1, user4);
        follow(user2, user3);
        follow(user2, user1);

        System.out.println("line -------------------------------------------");

        List<Photo> result= photoService.findAllPhotosOfFollowing(user1.getId());
        assertThat(result.size()).isEqualTo(6);
        assertTrue(result.stream()
                .allMatch(r -> (
                        r.getUser().getUsername().equals("kim") || r.getUser().getUsername().equals("lee") || r.getUser().getUsername().equals("choi")
                )));

        List<String> tags = new ArrayList<>();
        for (Photo photo : result) {
            photo.getPhotoTags().forEach(t ->
                tags.add(t.getTag().getName())
            );
        }
        assertThat(tags.size()).isEqualTo(7);

        assertTrue(tags.stream().allMatch(t->(
                t.equals("jpa")||t.equals("spring")||t.equals("aaaaa")||t.equals("태그")||t.equals("테스트")||t.equals("맛집")||t.equals("photo")
                )));


        List<Photo> result2 = photoService.findAllPhotosOfFollowing(user2.getId());
        assertThat(result2.size()).isEqualTo(6);
        assertTrue(result2.stream()
                .allMatch(r -> (
                        r.getUser().getUsername().equals("seo") || r.getUser().getUsername().equals("lee")
                )));
//        for (PhotoDto photo : allPhotosOfFollowing.getData()) {
//            System.out.println(photo.getImagePath());
//            System.out.println(photo.getTags());
//            System.out.println(photo.getContent());
//            System.out.println("--------------------------------------------------");
//        }

    }


    private void follow(User who, User whom) {
        followJpaRepository.save(new Follow(who, whom));
    }


    private User saveUser(String name, String password) {
        User user = new User(name, password);
        userJpaRepository.save(user);
        return user;

    }
}