package com.hj.sns.tag;

import com.hj.sns.tag.model.dto.TagNameSearchDto;
import com.hj.sns.tag.model.dto.TagSearchDto;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class TagSearchServiceTest {
    @Autowired
    private TagSearchService tagSearchService;
    @Autowired
    private TagJpaRepository tagJpaRepository;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("tag를 검색하여 사진들을 불러온다.")
    void findPhotoByTag() {
        Slice<TagSearchDto> result = tagSearchService.findPhotoByTag("tagA", PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC,
                "p.id")));
        assertThat(result.getContent().get(0).getPhotoId()).isEqualTo(7);
        assertThat(result.getContent().get(1).getPhotoId()).isEqualTo(4);
        assertThat(result.getContent().get(2).getPhotoId()).isEqualTo(2);
        assertThat(result.getContent().get(3).getPhotoId()).isEqualTo(1);
    }


    @Test
    @DisplayName("검색어를 포함하는 태그를 검색한다")
    void searchTag() {
        Slice<TagNameSearchDto> test = tagSearchService.searchTag("tag",
                PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "name")));
        assertThat(test.getContent().size()).isEqualTo(3);
        assertThat(test.getContent().get(0).getTagName()).isEqualTo("tagA");
        assertThat(test.getContent().get(0).getCount()).isEqualTo(4L);

        assertThat(test.getContent().get(1).getTagName()).isEqualTo("tagB");
        assertThat(test.getContent().get(1).getCount()).isEqualTo(3L);
        assertThat(test.getContent().get(2).getTagName()).isEqualTo("tagC");
        assertThat(test.getContent().get(2).getCount()).isEqualTo(2L);


    }

    @Test
    @DisplayName("검색어를 포함하는 태그를 검색한다. (검색 결과가 0개 일 때)")
    void searchTagV2() {
        Slice<TagNameSearchDto> test = tagSearchService.searchTag("#test",
                PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "name")));
        assertThat(test.getContent().size()).isEqualTo(0);
    }

}