package com.hj.sns.photo.model.dto;

import com.hj.sns.comment.model.dto.CommentDto;
import com.hj.sns.photo.model.Photo;
import com.hj.sns.tag.model.dto.TagDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PhotoDto {
    private Long photoId;
    private Long userId;
    private String username;
    private String imagePath;
    private String content;
    private List<TagDto> tags;
    private List<CommentDto> comments;
    public PhotoDto(Photo p){
        photoId=p.getId();
        userId=p.getUser().getId();
        username=p.getUser().getUsername();
        imagePath=p.getImagePath();
        content=p.getContent();
        tags=p.getPhotoTags().stream().map(pt->new TagDto(pt)).collect(Collectors.toList());
        comments=p.getComments().stream().map(c-> new CommentDto(c)).collect(Collectors.toList());
    }
}
