package com.hj.sns.photo.model;

import com.hj.sns.comment.model.Comment;
import com.hj.sns.user.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class MentionedUser {
    @Id
    @Column(name = "photo_user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentioned_user_id")
    private User mentionedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    protected MentionedUser() {
    }

    public MentionedUser(User user, Photo photo) {
        mentionedUser = user;
        this.photo = photo;
    }

}
