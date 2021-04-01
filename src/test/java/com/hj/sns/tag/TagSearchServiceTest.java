package com.hj.sns.tag;

import com.hj.sns.photoTag.model.dto.TagSearchDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TagSearchServiceTest {
    @Autowired
    private TagSearchService tagSearchService;


    @Test
    @DisplayName("tag를 검색하여 사진들을 불러온다.")
    void tagSearch(){
        Slice<TagSearchDto> result = tagSearchService.tagSearch("tagA", PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC,
                "p.id")));
        assertThat(result.getContent().get(0).getPhotoId()).isEqualTo(4);
        assertThat(result.getContent().get(1).getPhotoId()).isEqualTo(2);
        assertThat(result.getContent().get(2).getPhotoId()).isEqualTo(1);
    }
}