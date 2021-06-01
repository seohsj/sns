package com.hj.sns.follow.model.dto;

import com.hj.sns.follow.model.Follow;
import lombok.Data;

@Data
public class FollowerDto {
    private String username;

    public FollowerDto(Follow f) {
        username= f.getWho().getUsername();
    }
}
