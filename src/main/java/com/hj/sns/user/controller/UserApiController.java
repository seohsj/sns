package com.hj.sns.user.controller;

import com.hj.sns.user.model.User;
import com.hj.sns.user.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;


@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping("/api/users")
    public UserJoinResponse join(@RequestBody @Valid UserJoinRequest userJoinRequest) {

        User user =new User(userJoinRequest.getUsername(), userJoinRequest.getPassword());
        Long id = userService.save(user);
        return new UserJoinResponse(id);
    }


    @Data
    class UserJoinResponse {
        private Long id;
        UserJoinResponse(Long id) {
            this.id = id;
        }
    }
//1. class가 default면 생성자의 범위는 어디서부터 될까?


    @Data
    static class UserJoinRequest {
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;

    }
}
