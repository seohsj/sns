package com.hj.sns.photo.controller;

import com.hj.sns.photo.model.Photo;
import com.hj.sns.photo.service.PhotoService;
import com.hj.sns.tag.model.Tag;
import com.hj.sns.tag.service.TagService;
import com.hj.sns.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PhotoApiController {
    private final PhotoService photoService;
    private final UserService userService;
    private final TagService tagService;

    @PostMapping("/api/photos")
    public PhotoCreateResponse uploadPhoto(@RequestBody @Valid PhotoCreateRequest photoCreateRequest) {
//        List<Tag> tags = photoCreateRequest.getTags().stream()
//                .map(t -> new Tag(t.getName()))
//                .collect(Collectors.toList());
        Long photoId = photoService.save(photoCreateRequest.getUserId(), photoCreateRequest.getImagePath(), photoCreateRequest.getContent());
        return new PhotoCreateResponse(photoId);
    }


    @Data
    static class PhotoCreateRequest {
        @NotNull
        private Long userId;
        private String imagePath;
        private String content;
      //  private List<TagCreateDto> tags = new ArrayList<>();

    }

    @Data
    static class TagCreateDto {
        @NotNull
        private String name;
    }

    @Data
    @AllArgsConstructor
    class PhotoCreateResponse {
        private Long photoId;
    }


}
