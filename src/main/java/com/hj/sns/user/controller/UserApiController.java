package com.hj.sns.user.controller;

import com.hj.sns.user.model.User;
import com.hj.sns.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;


@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/users")
    public UserJoinResponse join(@RequestBody @Valid UserJoinRequest userJoinRequest) {
        User user = new User(userJoinRequest.getUsername(), userJoinRequest.getPassword());
        Long id = userService.save(user);
        return new UserJoinResponse(id);
    }


//    @GetMapping("/api/users/{username}")
//    public UserSearchResponse findUserById(@PathVariable String username) {
//        return userService.findUserById(userId);
//    }
////
//    @ExceptionHandler
//    public ResponseEntity<ErrorResponse> handleUserNotFoundEx(UserNotFoundException ex) {
//        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), ex.getMessage())
//                , HttpStatus.BAD_REQUEST);
//
//    }


    @Data
    class UserJoinResponse {
        private Long id;

        UserJoinResponse(Long id) {
            this.id = id;
        }
    }
//1. class가 default면 생성자의 범위는 어디서부터 될까?


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class UserJoinRequest {
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;

    }

//    class UserSearchResponse {
//        private String username;
//        private String
//    }
}
