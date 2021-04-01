package com.hj.sns.photoTag.model.dto;

import com.hj.sns.photoTag.model.PhotoTag;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TagSearchDto {
    private Long photoId;

    private String imagePath;
    private String content;
    private String uploaderName;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public TagSearchDto(PhotoTag p) {
        this.photoId = p.getPhoto().getId();
        this.imagePath = p.getPhoto().getImagePath();
        this.content = p.getPhoto().getContent();
        this.uploaderName = p.getPhoto().getUser().getUsername();
        this.createdDate = p.getPhoto().getCreatedDate();
        this.lastModifiedDate = p.getPhoto().getLastModifiedDate();
    }
}
