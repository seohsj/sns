package com.hj.sns.follow.query;

import lombok.Data;

@Data
public class FollowingQueryDto {
    private String username;
    public FollowingQueryDto(String username){
        this.username=username;
    }
}
