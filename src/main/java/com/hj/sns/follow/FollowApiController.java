package com.hj.sns.follow;

import com.hj.sns.follow.model.dto.FollowerDto;
import com.hj.sns.follow.model.dto.FollowingDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class FollowApiController {
    private final FollowService followService;


    @GetMapping("/api/{username}/followings")
    public Slice<FollowingDto> followingList(@PathVariable("username") String username, Pageable pageable) {
        return followService.findFollowingsPaging(username, pageable);
    }

    @GetMapping("/api/{username}/followers")
    public Slice<FollowerDto> followerList(@PathVariable("username") String username, Pageable pageable) {
        return followService.findFollowerPaging(username, pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/follows")
    public void requestFollow(@RequestBody @Valid FollowRequest followRequest) {
        followService.follow(followRequest.getWho(), followRequest.getWhom());
    }
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/api/follows")
    public void requestUnfollow(@RequestBody @Valid UnfollowRequest unfollowRequest){
        followService.unfollow(unfollowRequest.getWho(), unfollowRequest.getWhom());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class FollowRequest {
        @NotNull
        private String who;
        @NotNull
        private String whom;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class UnfollowRequest {
        @NotNull
        private String who;
        @NotNull
        private String whom;
    }

}
