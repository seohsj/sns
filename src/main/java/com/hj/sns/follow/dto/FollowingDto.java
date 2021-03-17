package com.hj.sns.follow.dto;

import com.hj.sns.follow.Follow;
import lombok.Data;

@Data
public class FollowingDto {
    private String username;
    public FollowingDto(Follow follow){
        username= follow.getWhom().getUsername();
    }
}
