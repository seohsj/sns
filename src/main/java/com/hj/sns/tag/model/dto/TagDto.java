package com.hj.sns.tag.model.dto;

import com.hj.sns.photo.model.PhotoTag;
import lombok.Data;

@Data
public class TagDto {
    private String name;
    public TagDto(PhotoTag photoTag){
        name=photoTag.getTag().getName();
    }
}
