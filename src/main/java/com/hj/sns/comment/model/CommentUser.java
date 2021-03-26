package com.hj.sns.comment.model;

import com.hj.sns.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CommentUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentioned_user_id")
    private User mentionedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "comment_id")
    private Comment comment;

    protected CommentUser(){}
    public CommentUser(User user, Comment comment){
        mentionedUser=user;
        this.comment=comment;

    }

}
