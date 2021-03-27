package com.hj.sns.comment.model;

import com.hj.sns.common.BaseTime;
import com.hj.sns.photo.model.Photo;
import com.hj.sns.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentUser> commentUsers = new ArrayList<>();

    private static final Pattern mentionedUserPattern = Pattern.compile("@([0-9a-zA-Z가-힣_]+)");

    public Comment(User user, String content, Photo photo) {
        this.user = user;
        this.content = content;
        this.photo = photo;
    }

    protected Comment() {

    }

    public List<String> extractMentionedUsers() {
        List<String> userNames = new ArrayList<>();
        Matcher matcher = mentionedUserPattern.matcher(content);
        while (matcher.find()) {
            userNames.add(matcher.group().substring(1));
        }
        return userNames;
    }

    public void addMentionedUser(User user) {
        CommentUser commentUser = new CommentUser(user, this);
        commentUsers.add(commentUser);

    }


}

