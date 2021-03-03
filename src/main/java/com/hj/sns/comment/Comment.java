package com.hj.sns.comment;

import com.hj.sns.base.BaseTime;
import com.hj.sns.photo.model.Photo;
import com.hj.sns.user.model.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private Photo photo;


}

