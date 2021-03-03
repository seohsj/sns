package com.hj.sns.tag.service;

import com.hj.sns.tag.model.Tag;
import com.hj.sns.tag.repository.TagJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TagServiceTest {
    @Autowired
    private TagService tagService;
    @Autowired
    private TagJpaRepository tagJpaRepository;


    @Test
    @DisplayName("기존에 저장되지 않은 태그라면 저장한다.")
    void save() {
        Tag tag = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        tags.add(tag2);
        tagService.saveTags(tags);
        assertThat(tag).isEqualTo(tagJpaRepository.findById(tag.getId()).get());
        assertThat(tag2).isEqualTo(tagJpaRepository.findById(tag2.getId()).get());


    }

    @Test
    @DisplayName("빈 태그 리스트를 저장하면 아무것도 저장되지 않는다.")
    void saveEmptyTags() {
        List<Tag> tags = new ArrayList<>();

        tagService.saveTags(tags);
        assertThat(tagJpaRepository.findAll().size()).isEqualTo(0);


    }

    @Test
    @DisplayName("이미 같은 태그가 저장되어있다면 새로 저장하지 않는다.")
    void saveDuplicateTag() {
        Tag tag = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        tags.add(tag2);
        tagService.saveTags(tags);

        Tag tag2_2 = new Tag("tag2");
        List<Tag> anotherTags = new ArrayList<>();
        anotherTags.add(tag2_2);


        assertThat(tagJpaRepository.findAll().size()).isEqualTo(2);
        assertThat(tagJpaRepository
                .findByName("tag2")
                .stream()
                .collect(Collectors.toList()).size()).isEqualTo(1);
    }

}