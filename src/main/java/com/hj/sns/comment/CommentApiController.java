package com.hj.sns.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/photos/{photoId}/comments")
    public CommentCreateResponse commentCreate(@PathVariable("photoId") Long photoId, CommentCreateRequest commentCreateRequest){
        Long id= commentService.writeComment(photoId, commentCreateRequest.writerId, commentCreateRequest.content);
        return new CommentCreateResponse(id);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class CommentCreateResponse{
        private Long id;


    }

    @Data
    @AllArgsConstructor
    static class CommentCreateRequest{
        private Long writerId;
        private String content;

    }
}
