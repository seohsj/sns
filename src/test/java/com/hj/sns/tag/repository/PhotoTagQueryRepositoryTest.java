package com.hj.sns.tag.repository;

import com.hj.sns.tag.model.dto.TagSearchDto;
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
class PhotoTagQueryRepositoryTest {

    @Autowired
    PhotoTagQueryRepository photoTagQueryRepository;

    @Test
    void tagSearch(){
        Slice<TagSearchDto> tagSearchResult = photoTagQueryRepository.findTagSearchResult("tagA", PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC,
                "photo.id")));
        List<TagSearchDto> result = tagSearchResult.getContent();
        assertThat(result.get(0).getPhotoId()).isEqualTo(4);
        assertThat(result.get(1).getPhotoId()).isEqualTo(2);
        assertThat(result.get(2).getPhotoId()).isEqualTo(1);

    }
}