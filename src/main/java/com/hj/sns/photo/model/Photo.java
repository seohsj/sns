package com.hj.sns.photo.model;

import com.hj.sns.base.BaseTime;
import com.hj.sns.comment.Comment;
import com.hj.sns.domain.PhotoTag;
import com.hj.sns.tag.model.Tag;
import com.hj.sns.user.model.User;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Getter
public class Photo extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    @Column(nullable = false)
    private String imagePath;

    @Lob
    private String content;


    @OneToMany(mappedBy = "photo", cascade = CascadeType.ALL)
    private List<PhotoTag> photoTags = new ArrayList<>();


    @OneToMany(mappedBy = "photo")
    private List<Comment> comments = new ArrayList<>();


    public Photo(User user, String imagePath, String content) {
        this.user = user;
        this.imagePath = imagePath;
        this.content = content;
    }



 /*   public Photo(User user, String imagePath, String content, List<Tag> tags) {
        this.user = user;
        this.imagePath = imagePath;
        this.content = content;
        addPhotoTags(tags);
    }*/


    protected Photo() {
    }

    public List<Tag> extractTags() {
        List<Tag> tags = new ArrayList<>();
        String regx = "\\#([0-9a-zA-Z가-힣]+)";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher= pattern.matcher(content);
        while (matcher.find()) {
            tags.add(new Tag(matcher.group().substring(1)));
            if (matcher.group() == null) break;
        }
        return tags;
    }

    public void addPhotoTags(List<Tag> tags) {
        for (Tag tag : tags) {
            PhotoTag photoTag = new PhotoTag(this, tag);
            this.photoTags.add(photoTag);

        }
    }
}
