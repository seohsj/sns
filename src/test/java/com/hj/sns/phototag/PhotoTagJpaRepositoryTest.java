package com.hj.sns.phototag;

import com.hj.sns.photo.PhotoJpaRepository;
import com.hj.sns.photo.model.Photo;
import com.hj.sns.photoTag.PhotoTagJpaRepository;
import com.hj.sns.photoTag.PhotoTag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class PhotoTagJpaRepositoryTest {

    @Autowired
    PhotoTagJpaRepository photoTagJpaRepository;
    @Autowired
    PhotoJpaRepository photoJpaRepository;
    @Autowired
    EntityManager em;
    @Test
    @DisplayName("tag를 기준으로 photo를 조회한다.")
    void tagSearch(){
        Slice<PhotoTag> tagSearchResult = photoTagJpaRepository.findPhotoByTag("tagA", PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC,
                "photo.id")));
        List<PhotoTag> result = tagSearchResult.getContent();
        assertThat(result.get(0).getPhoto().getId()).isEqualTo(7);
        assertThat(result.get(1).getPhoto().getId()).isEqualTo(4);
        assertThat(result.get(2).getPhoto().getId()).isEqualTo(2);
        assertThat(result.get(3).getPhoto().getId()).isEqualTo(1);

    }

    @Test
    @DisplayName("photo를 기준으로 PhotoTag를 조회한다. 연관된 Tag도 페치조인하여 가져온다.")
    void findPhotoTagByPhoto(){
        Photo photo = photoJpaRepository.findById(1L).orElse(null);
        List<PhotoTag> photoTags = photoTagJpaRepository.findPhotoTagByPhoto(photo);
        em.flush();
        em.clear();
        assertThat(photoTags.size()).isEqualTo(3);
        for(PhotoTag pt: photoTags){
            String tagName = pt.getTag().getName();
            assertTrue(tagName.equals("tagA")||tagName.equals("tagB")||tagName.equals("tagC"));
        }
    }


}