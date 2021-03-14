package com.hj.sns.exception;

import com.hj.sns.follow.exception.FollowAlreadyExistException;
import com.hj.sns.user.exception.UserAlreadyExistException;
import com.hj.sns.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistException.class)
    public ErrorResponse userAlreadyExistException(UserAlreadyExistException e) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse userNotFoundException(UserNotFoundException e) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FollowAlreadyExistException.class)
    public ErrorResponse followAlreadyExistException(FollowAlreadyExistException e) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }


}