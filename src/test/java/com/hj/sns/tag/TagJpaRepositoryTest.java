package com.hj.sns.tag;

import com.hj.sns.tag.model.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class TagJpaRepositoryTest {

    @Autowired private TagJpaRepository tagJpaRepository;
    @Autowired private EntityManager em;

    @Test
    @DisplayName("연관된 photoTag가 없는 tag ids를 구한다.")
    void getOrphanTag(){
        List<Long> tagIds= new ArrayList<>();
        tagIds.add(1L);
        tagIds.add(2L);
        tagIds.add(3L);
        tagIds.add(4L);
        tagIds.add(5L);
        tagIds.add(6L);
        List<Long> tags = tagJpaRepository.getOrphanTagId(tagIds);
        assertThat(tags.size()).isEqualTo(1);
        assertThat(tags.get(0)).isEqualTo(6L);
    }

}
