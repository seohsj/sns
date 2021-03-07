package com.hj.sns.comment.model.dto;

import com.hj.sns.comment.model.Comment;
import lombok.Data;

@Data
public class CommentDto {
    private String writerName;
    private String content;

    public CommentDto(Comment c) {
        writerName = c.getUser().getUsername();
        content = c.getContent();
    }

}
