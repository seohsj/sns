package com.hj.sns.comment.model;

import com.hj.sns.photo.model.Photo;
import com.hj.sns.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class CommentTest {
    @Test
    @DisplayName("댓글에서 태그된 유저를 뽑아낸다.")
    void addMentionedUser(){
        Photo photo = new Photo(new User("photoUser", "password"), "imagePath", "content");
        User user= new User("user","password");
        Comment comment = new Comment(user, "@User_1!! testtest @userA@userB", photo);

        List<String> names = comment.extractMentionedUsers();

        assertThat(names.get(0)).isEqualTo("User_1");
        assertThat(names.get(1)).isEqualTo("userA");
        assertThat(names.get(2)).isEqualTo("userB");


    }

}