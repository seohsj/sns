package com.hj.sns.user.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String MESSAGE = "가입하지 않은 회원입니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }


}
