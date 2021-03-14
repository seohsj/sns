package com.hj.sns.follow;

import com.hj.sns.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class FollowApiController {
    private final FollowService followService;

//    @GetMapping("/api/{username}/followings")
//    public Page<FollowingResponse> followingList(@PathVariable("username") String username){
//
//    }
    @GetMapping("/api/{username}/followers")
    public void followerList(@PathVariable("username") String username){


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

    @Data
    static class FollowingResponse{
        private String followingUsername;
        FollowingResponse(User user){
            followingUsername=user.getUsername();
        }
    }

}
