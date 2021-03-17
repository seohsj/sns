package com.hj.sns.user.exception;

public class UserAlreadyExistException extends RuntimeException {
    private static final String MESSAGE = "이미 존재하는 회원입니다.";

    public UserAlreadyExistException() {
        super(MESSAGE);
    }
}
