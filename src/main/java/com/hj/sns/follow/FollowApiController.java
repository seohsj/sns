package com.hj.sns.follow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class FollowApiController {
    private final FollowService followService;

    @GetMapping("/api/{username}/follows")
    public void followsList(@PathVariable("username") String username){


    }

    @PostMapping("/api/follows")
    public void requestFollow(@RequestBody @Valid  FollowRequest followRequest){

    }

    @Data
    @AllArgsConstructor
    static class FollowRequest{
        @NotNull
        private Long whoId;
        @NotNull
        private Long whomId;
    }
}
