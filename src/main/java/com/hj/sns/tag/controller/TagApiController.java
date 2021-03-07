package com.hj.sns.tag.controller;

import com.hj.sns.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TagApiController {
    private final TagService tagService;


    //@GetMapping("/api/tags/{name}")
    //사진 hashtag로 찾기

}
