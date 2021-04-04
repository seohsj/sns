package com.hj.sns.tag;

import com.hj.sns.tag.model.Tag;
import com.hj.sns.tag.model.dto.TagNameSearchDto;
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
class TagQueryRepositoryTest {
    @Autowired TagQueryRepository tagQueryRepository;
    @Autowired TagJpaRepository tagJpaRepository;
    @Autowired
    EntityManager em;
    @Test
    @DisplayName("검색어를 포함한 태그를 페이징해서 조회한다")
    void tagSearch(){

        Slice<TagNameSearchDto> test = tagQueryRepository.searchTag("tag",
                PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "name")));
        assertThat(test.getContent().size()).isEqualTo(7);
        assertThat(test.getContent().get(0).getTagName()).isEqualTo("tagA");
        assertThat(test.getContent().get(0).getCount()).isEqualTo(4L);

        assertThat(test.getContent().get(1).getTagName()).isEqualTo("tagB");
        assertThat(test.getContent().get(1).getCount()).isEqualTo(3L);
        assertThat(test.getContent().get(2).getTagName()).isEqualTo("tagC");
        assertThat(test.getContent().get(2).getCount()).isEqualTo(2L);
        assertThat(test.getContent().get(3).getTagName()).isEqualTo("tagD");
        assertThat(test.getContent().get(3).getCount()).isEqualTo(1L);

        assertThat(test.getContent().get(4).getTagName()).isEqualTo("tagE");
        assertThat(test.getContent().get(4).getCount()).isEqualTo(3L);
        assertThat(test.getContent().get(5).getTagName()).isEqualTo("tagF");
        assertThat(test.getContent().get(5).getCount()).isEqualTo(0);
        assertThat(test.getContent().get(6).getTagName()).isEqualTo("tagG");
        assertThat(test.getContent().get(6).getCount()).isEqualTo(1L);



    }
}