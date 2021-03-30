package com.hj.sns.tag;

import com.hj.sns.tag.model.dto.TagSearchDto;
import com.hj.sns.tag.repository.PhotoTagQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagSearchService {

    private final PhotoTagQueryRepository photoTagQueryRepository;

    public Slice<TagSearchDto> tagSearch(String tagName, Pageable pageable){
        return photoTagQueryRepository.findTagSearchResult(tagName, pageable);
    }

}
