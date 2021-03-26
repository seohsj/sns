package com.hj.sns.comment.exception;

public class CommentNotFoundException extends RuntimeException {
    private static final String MESSAGE = "등록되지 않은 comment_id 입니다.";

    public CommentNotFoundException() {
        super(MESSAGE);
    }

}
