package com.hj.sns.photo.controller;

import com.hj.sns.photo.model.dto.PhotoDto;
import com.hj.sns.photo.service.PhotoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

/*
* TODO: paging하기
*/


@RestController
@RequiredArgsConstructor
public class PhotoApiController {
    private final PhotoService photoService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/photos")
    public PhotoCreateResponse uploadPhoto(@RequestBody @Valid PhotoCreateRequest photoCreateRequest) {
        Long photoId = photoService.save(photoCreateRequest.getUserId(), photoCreateRequest.getImagePath(), photoCreateRequest.getContent());
        return new PhotoCreateResponse(photoId);
    }

    @GetMapping("/api/photos/{username}")
    public Slice<PhotoDto> findPhotos(@PathVariable("username") String username, Pageable pageable){
        return photoService.findPhotoByUser(username, pageable);
    }




    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class PhotoCreateRequest {
        @NotNull
        private Long userId;
        @NotNull
        private String imagePath;
        private String content;



    }

    @Data
    @AllArgsConstructor
    static class PhotoCreateResponse {
        private Long photoId;
    }

}
