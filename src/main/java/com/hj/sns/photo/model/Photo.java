package com.hj.sns.photo.model;

import com.hj.sns.common.BaseTime;
import com.hj.sns.comment.model.Comment;
import com.hj.sns.tag.model.Tag;
import com.hj.sns.user.User;
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


    @OneToMany(mappedBy = "photo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhotoTag> photoTags = new ArrayList<>();


    @OneToMany(mappedBy = "photo", cascade= CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();


    private static final Pattern pattern = Pattern.compile("#([0-9a-zA-Z가-힣]+)");

    public Photo(User user, String imagePath, String content) {
        this.user = user;
        this.imagePath = imagePath;
        this.content = content;
    }

    protected Photo() {

    }

    public List<Tag> extractTags() {
        List<Tag> tags = new ArrayList<>();
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            tags.add(new Tag(matcher.group().substring(1)));
        }
        return tags;
    }

    public void addPhotoTags(List<Tag> tags) {
        for (Tag tag : tags) {
            PhotoTag photoTag = new PhotoTag(this, tag);
            this.photoTags.add(photoTag);

        }
    }
    public void updatePhotoTags(List<Tag> tags) {
        photoTags.clear();
        addPhotoTags(tags);
    }

    public void updateImagePath(String imagePath) {
        this.imagePath = imagePath;

    }

    public void updateContent(String content) {
        this.content = content;
    }


}
