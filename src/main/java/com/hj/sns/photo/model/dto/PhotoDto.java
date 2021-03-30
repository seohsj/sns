package com.hj.sns.photo.model.dto;

import com.hj.sns.comment.model.dto.CommentDto;
import com.hj.sns.photo.model.Photo;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PhotoDto {
    private String imagePath;
    private String content;
    private List<PhotoTagDto> tags;
    private List<CommentDto> comments;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private List<MentionedUserDto> mentionedUsers;
    public PhotoDto(Photo p) {
        imagePath = p.getImagePath();
        content = p.getContent();
        tags = p.getPhotoTags().stream().map(PhotoTagDto::new).collect(Collectors.toList());
        comments = p.getComments().stream().map(CommentDto::new).collect(Collectors.toList());
        createdDate = p.getCreatedDate();
        lastModifiedDate = p.getLastModifiedDate();
        mentionedUsers = p.getMentionedUsers().stream().map(MentionedUserDto::new).collect(Collectors.toList());
    }


}
