package com.hj.sns.tag;

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

    @GetMapping("/api/tags/{name}")
    public  Slice<TagSearchDto> tagSearch(@PathVariable("name")String name, Pageable pageable){
        return tagSearchService.tagSearch(name, pageable);
    }

}
