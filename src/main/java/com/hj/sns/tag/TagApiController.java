package com.hj.sns.tag;

import com.hj.sns.tag.model.dto.TagNameSearchDto;
import com.hj.sns.tag.model.dto.TagSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TagApiController {
    private final TagSearchService tagSearchService;

    @GetMapping("/api/tags/{name}/photos")
    public Slice<TagSearchDto> findPhotoByTag(@PathVariable("name") String name, Pageable pageable) {
        return tagSearchService.findPhotoByTag(name, pageable);
    }
    @GetMapping("/api/tags/{searchWord}")
    public Slice<TagNameSearchDto> searchTag(@PathVariable("searchWord") String name, Pageable pageable) {
        return tagSearchService.searchTag(name, pageable);
    }

}
