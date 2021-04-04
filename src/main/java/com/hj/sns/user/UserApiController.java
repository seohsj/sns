package com.hj.sns.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;


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


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class UserJoinRequest {
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;

    }

    @Data
    static class UserJoinResponse {
        private Long id;
        UserJoinResponse(Long id) {
            this.id = id;
        }
    }


}
