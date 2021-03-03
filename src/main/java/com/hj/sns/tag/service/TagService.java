package com.hj.sns.tag.service;


import com.hj.sns.tag.model.Tag;
import com.hj.sns.tag.repository.TagJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagJpaRepository tagJpaRepository;

    @Transactional
    public void saveTags(List<Tag> tags) {
        for (Tag tag : tags) {
            if (tagJpaRepository.findByName(tag.getName()).isEmpty()) tagJpaRepository.save(tag);
        }
    }

   /* public List<Tag> extractTags(String content) {
       return Arrays.stream(content.split("#"))
                .filter(c -> c.startsWith("#"))
                .map(c -> {
                    int idx = c.indexOf(" ");
                    return new Tag(c.substring(0, idx));
                })
                .collect(Collectors.toList());

    }*/

}
