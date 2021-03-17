package com.hj.sns.follow.exception;

public class FollowAlreadyExistException extends RuntimeException{
    private final static String MESSAGE="이미 Follow 했습니다.";

    public FollowAlreadyExistException() {
        super(MESSAGE);
    }
}
