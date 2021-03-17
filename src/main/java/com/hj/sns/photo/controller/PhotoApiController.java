package com.hj.sns.photo.controller;


import com.hj.sns.photo.service.PhotoService;
import com.hj.sns.tag.service.TagService;
import com.hj.sns.user.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

    /*following photo+ 내 photo 순서대로 가져오기
     * paging하기*/
//    @GetMapping("/api/phtos")
//    public Result<PhotoDto> getPhotos(@RequestBody @Valid PhotoRequest photoRequest) {
//
//        List<PhotoDto> photos = new ArrayList<>();
//        photoList.forEach(p ->
//                photos.add(new PhotoDto(p))
//        );
//    }


    //  @GetMapping("/api/photos/{userName}")
    //특정유저포스팅 조회
//여기서 userName이 자신인 경우 사진 수정등하기
//    @Data
//    static class PhotoRequest {
//        @NotNull
//        private Long userId;
//
//    }


    @Data
    static class PhotoCreateRequest {
        @NotNull
        private Long userId;
        private String imagePath;
        private String content;

    }

    @Data
    static class TagCreateDto {
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class PhotoCreateResponse {
        private Long photoId;
    }
    @Data
    static class Result<T> {
        private T data;

        public Result(T data) {
            this.data = data;
        }
    }

}
