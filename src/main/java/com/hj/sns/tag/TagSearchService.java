package com.hj.sns.tag;

import com.hj.sns.photoTag.model.dto.TagSearchDto;
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

    public Slice<TagSearchDto> tagSearch(String tagName, Pageable pageable){
        return photoTagJpaRepository.findTagSearchResult(tagName, pageable).map(p->new TagSearchDto(p));
    }

}
