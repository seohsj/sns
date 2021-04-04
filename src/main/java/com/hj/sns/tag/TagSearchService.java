package com.hj.sns.tag;

import com.hj.sns.tag.model.dto.TagNameSearchDto;
import com.hj.sns.tag.model.dto.TagSearchDto;
import com.hj.sns.photoTag.PhotoTagJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagSearchService {

    private final PhotoTagJpaRepository photoTagJpaRepository;
    private final TagQueryRepository tagQueryRepository;

    public Slice<TagSearchDto> findPhotoByTag(String tagName, Pageable pageable) {
        return photoTagJpaRepository.findPhotoByTag(tagName, pageable).map(p -> new TagSearchDto(p));
    }

    public Slice<TagNameSearchDto> searchTag(String name, Pageable pageable) {
        return tagQueryRepository.searchTag(name, pageable);

    }
}
