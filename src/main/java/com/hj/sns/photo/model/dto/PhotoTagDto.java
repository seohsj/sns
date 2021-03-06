package com.hj.sns.photo.model.dto;

import com.hj.sns.photoTag.PhotoTag;
import lombok.Data;

@Data
public class PhotoTagDto {
    String tagName;

    public PhotoTagDto(PhotoTag photoTag){
        tagName=photoTag.getTag().getName();
    }
}
