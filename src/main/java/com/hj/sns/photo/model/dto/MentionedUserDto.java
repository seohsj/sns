package com.hj.sns.photo.model.dto;

import com.hj.sns.photo.model.PhotoUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MentionedUserDto {
    private String username;
    public MentionedUserDto(PhotoUser p){
        username=p.getMentionedUser().getUsername();
    }

}
