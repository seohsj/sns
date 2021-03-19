package com.hj.sns.photo.controller;


import com.hj.sns.photo.service.PhotoService;
import com.hj.sns.tag.service.TagService;
import com.hj.sns.user.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/*
* TODO: paging하기
*/


@RestController
@RequiredArgsConstructor
public class PhotoApiController {
    private final PhotoService photoService;
    private final UserService userService;
    private final TagService tagService;

    @PostMapping("/api/photos")
    public PhotoCreateResponse uploadPhoto(@RequestBody @Valid PhotoCreateRequest photoCreateRequest) {
        Long photoId = photoService.save(photoCreateRequest.getUserId(), photoCreateRequest.getImagePath(), photoCreateRequest.getContent());
        return new PhotoCreateResponse(photoId);
    }



    @Data
    static class PhotoCreateRequest {
        @NotNull
        private Long userId;
        private String imagePath;
        private String content;

    }

    @Data
    @AllArgsConstructor
    static class PhotoCreateResponse {
        private Long photoId;
    }

}
