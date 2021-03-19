package com.hj.sns.photo.model.dto;

import com.hj.sns.comment.model.Comment;
import com.hj.sns.comment.model.dto.CommentDto;
import com.hj.sns.photo.model.Photo;
import com.hj.sns.photo.model.PhotoTag;
import com.hj.sns.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PhotoDto {
    private String imagePath;
    private String content;
    private List<PhotoTagDto> tags;
    private List<CommentDto> comments;

    public PhotoDto(Photo p) {
        imagePath = p.getImagePath();
        content = p.getContent();
        tags = p.getPhotoTags().stream().map(PhotoTagDto::new).collect(Collectors.toList());
        comments = p.getComments().stream().map(CommentDto::new).collect(Collectors.toList());

    }


}
