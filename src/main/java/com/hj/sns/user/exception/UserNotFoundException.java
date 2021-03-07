package com.hj.sns.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super();
    }


    public UserNotFoundException(String message) {
        super(message);
    }


}
