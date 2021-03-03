package com.hj.sns.photo.service;

import com.hj.sns.photo.model.Photo;
import com.hj.sns.photo.model.PhotoTest;
import com.hj.sns.photo.repository.PhotoJpaRepository;
import com.hj.sns.tag.repository.TagJpaRepository;
import com.hj.sns.user.model.User;
import com.hj.sns.user.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PhotoServiceTest {

    @Autowired
    private PhotoService photoService;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private TagJpaRepository tagJpaRepository;
    @Autowired
    private PhotoJpaRepository photoJpaRepository;
//    public Long save(Long userId, String imagePath, String content, List<Tag> tags){
//        User user= userRepository.findById(userId).get();
//        Photo photo = new Photo(user, imagePath, content, tags);
//        photoRepository.save(photo);
//        return photo.getId();
//    }


    @Test
    void save() {
        User user = new User("userA", "password");
        userJpaRepository.save(user);
        String tag1 = "tag1";
        String tag2 = "tag2";

        String imagePath = "imagePath1";
        String content = "content1";
        content = content + "#" + tag1 + "#" + tag2;
        Long photoId = photoService.save(user.getId(), imagePath, content);
        Photo findPhoto = photoJpaRepository.findById(photoId).get();

        assertThat(findPhoto.getPhotoTags().size()).isEqualTo(2);
        assertThat(findPhoto.getUser().getId()).isEqualTo(user.getId());
        assertThat(findPhoto.getImagePath()).isEqualTo(imagePath);
        assertThat(findPhoto.getPhotoTags().stream()
                .allMatch(pt -> (
                        pt.getTag().getName().equals(tag1) || pt.getTag().getName().equals(tag2)
                ))).isEqualTo(true);
    }
}