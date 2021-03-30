package com.hj.sns.tag.model.dto;

import com.hj.sns.tag.model.PhotoTag;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TagSearchDto {
    private Long photoId;
    private String imagePath;
    private String content;
    private String upLoaderName;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public TagSearchDto(Long photoId, String imagePath, String content, String upLoaderName, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.photoId = photoId;
        this.imagePath = imagePath;
        this.content = content;
        this.upLoaderName = upLoaderName;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
