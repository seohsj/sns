package com.hj.sns.phototag;

import com.hj.sns.photoTag.PhotoTagJpaRepository;
import com.hj.sns.photoTag.model.PhotoTag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PhotoTagJpaRepositoryTest {

    @Autowired
    PhotoTagJpaRepository photoTagJpaRepository;

    @Test
    void tagSearch(){
        Slice<PhotoTag> tagSearchResult = photoTagJpaRepository.findTagSearchResult("tagA", PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC,
                "photo.id")));
        List<PhotoTag> result = tagSearchResult.getContent();
        assertThat(result.get(0).getPhoto().getId()).isEqualTo(4);
        assertThat(result.get(1).getPhoto().getId()).isEqualTo(2);
        assertThat(result.get(2).getPhoto().getId()).isEqualTo(1);

    }
}