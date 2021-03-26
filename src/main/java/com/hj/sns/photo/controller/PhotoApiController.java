package com.hj.sns.photo.controller;

import com.hj.sns.photo.model.dto.PhotoDto;
import com.hj.sns.photo.model.dto.PhotoFeedDto;
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

    @PatchMapping("/api/photos/{photoId}")
    public PhotoUpdateResponse updatePhoto(@PathVariable("photoId") Long photoId,@RequestBody PhotoUpdateRequest photoUpdateRequest) {
        photoService.updatePhoto(photoId, photoUpdateRequest.getImagePath(), photoUpdateRequest.getContent());
        return new PhotoUpdateResponse(photoId);
    }

    @DeleteMapping("/api/photos/{photoId}")
    public PhotoDeleteResponse deletePhoto(@PathVariable("photoId") Long photoId){
        photoService.deletePhoto(photoId);
        return new PhotoDeleteResponse(true);
    }

    @GetMapping("/api/photos/{username}")
    public Slice<PhotoDto> findPhotos(@PathVariable("username") String username, Pageable pageable){
        return photoService.findPhotoByUser(username, pageable);
    }

    @GetMapping("/api/feeds/{username}")
    public Slice<PhotoFeedDto> getUserFeed(@PathVariable("username") String username, Pageable pageable){
        return  photoService.getUserFeed(username, pageable);
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


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class PhotoUpdateRequest{
        private String imagePath;
        private String content;
    }

    @Data
    @AllArgsConstructor
    static class PhotoUpdateResponse{
        private Long photoId;
    }
    @Data
    @AllArgsConstructor
    static class PhotoDeleteResponse{
        private boolean deleted;

    }

}
