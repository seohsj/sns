package com.hj.sns.follow.exception;

public class FollowNotFoundException extends RuntimeException{
    private static final String MESSAGE="Follow관계가 아닙니다.";
    public FollowNotFoundException() {
        super(MESSAGE);
    }

}
