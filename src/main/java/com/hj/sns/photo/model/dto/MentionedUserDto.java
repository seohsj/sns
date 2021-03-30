package com.hj.sns.photo.model.dto;

import com.hj.sns.photo.model.MentionedUser;
import lombok.Data;

@Data
public class MentionedUserDto {
    private String username;
    public MentionedUserDto(MentionedUser p){
        username=p.getMentionedUser().getUsername();
    }

}
