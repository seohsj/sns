package com.hj.sns.comment.model.dto;

import com.hj.sns.comment.model.Comment;
import lombok.Data;

@Data
public class CommentDto {
    String username;
    String content;

    public CommentDto(Comment comment){
        username= comment.getUser().getUsername();
        content= comment.getContent();
    }
}
