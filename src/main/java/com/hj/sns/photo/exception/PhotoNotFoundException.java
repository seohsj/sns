package com.hj.sns.photo.exception;

public class PhotoNotFoundException extends RuntimeException{
    private static final String MESSAGE = "해당 사진이 존재하지 않습니다.";
    public PhotoNotFoundException() {
        super(MESSAGE);
    }


}
