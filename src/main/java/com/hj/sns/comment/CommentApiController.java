package com.hj.sns.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/photos/{photoId}/comments")
    public CommentCreateResponse createComment(@PathVariable("photoId") Long photoId, @RequestBody @Valid CommentCreateRequest commentCreateRequest){
        Long id= commentService.writeComment(photoId, commentCreateRequest.writerId, commentCreateRequest.content);
        return new CommentCreateResponse(id);
    }

    @DeleteMapping("/api/comments/{commentId}")
    public CommentDeleteResponse deleteComment(@PathVariable("commentId") Long commentId){
        commentService.deleteComment(commentId);
        return new CommentDeleteResponse(true);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class CommentCreateRequest{
        @NotNull
        private Long writerId;
        private String content;
    }


    @Data
    @AllArgsConstructor
    static class CommentCreateResponse{
        private Long commentId;
    }


    @Data
    @AllArgsConstructor
    static class CommentDeleteResponse{
        private boolean deleted;
    }



}
