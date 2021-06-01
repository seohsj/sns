package com.hj.sns.photo.model;

import com.hj.sns.user.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PhotoTest {

    @Test
    @DisplayName("Photo content에서 해시태그를 추출한다.")
    void extractTags() {

        User user = new User("seo", "afldks");
        String imagePath = "imagePath";
        String content = "abcd#음식 defs#abc#ABC#";
        Photo photo = new Photo(user, imagePath, content);
        List<String> tags = photo.extractTags();
        assertThat(tags.size()).isEqualTo(3);
        assertThat(tags.get(0)).isEqualTo("음식");
        assertThat(tags.get(1)).isEqualTo("abc");
        assertThat(tags.get(2)).isEqualTo("ABC");


        String content2 = "#123식당?#phone#코트 #1######";
        Photo photo2 = new Photo(user, imagePath, content2);
        List<String> tags2 = photo2.extractTags();
        assertThat(tags2.size()).isEqualTo(4);
        assertThat(tags2.get(0)).isEqualTo("123식당");
        assertThat(tags2.get(1)).isEqualTo("phone");
        assertThat(tags2.get(2)).isEqualTo("코트");
        assertThat(tags2.get(3)).isEqualTo("1");
    }
    @Test
    @DisplayName("Photo content에서 태그된 유저들과 해시태그를 추출한다.")
    void extractMentionedUsers() {

        User user = new User("seo", "afldks");
        String imagePath = "imagePath";
        String content = "abcd#음식 @John defs#abc#ABC#@James";
        Photo photo = new Photo(user, imagePath, content);

        List<String> tags = photo.extractTags();

        assertThat(tags.size()).isEqualTo(3);
        assertThat(tags.get(0)).isEqualTo("음식");
        assertThat(tags.get(1)).isEqualTo("abc");
        assertThat(tags.get(2)).isEqualTo("ABC");

        List<String> userNames= photo.extractMentionedUsers();
        assertThat(userNames.size()).isEqualTo(2);
        assertThat(userNames.get(0)).isEqualTo("John");
        assertThat(userNames.get(1)).isEqualTo("James");


        String content2 = "#123식당?@Sally#phone#코트 @Anna_132#1######";
        Photo photo2 = new Photo(user, imagePath, content2);
        List<String> tags2 = photo2.extractTags();
        assertThat(tags2.size()).isEqualTo(4);
        assertThat(tags2.get(0)).isEqualTo("123식당");
        assertThat(tags2.get(1)).isEqualTo("phone");
        assertThat(tags2.get(2)).isEqualTo("코트");
        assertThat(tags2.get(3)).isEqualTo("1");
        List<String> userNames2 = photo2.extractMentionedUsers();
        assertThat(userNames2.size()).isEqualTo(2);
        assertThat(userNames2.get(0)).isEqualTo("Sally");
        assertThat(userNames2.get(1)).isEqualTo("Anna_132");

    }


}

