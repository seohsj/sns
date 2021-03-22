package com.hj.sns.photo.model;

import com.hj.sns.tag.model.Tag;
import com.hj.sns.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PhotoTest {

    @Test
    @DisplayName("Photo content에서 태그들을 추출한다.")
    void extractTags() {

        User user = new User("seo", "afldks");
        String imagePath = "imagePath";
        String content = "abcd#음식 defs#abc#ABC#";
        Photo photo = new Photo(user, imagePath, content);
        List<Tag> tags = photo.extractTags();
        assertThat(tags.size()).isEqualTo(3);
        assertThat(tags.get(0).getName()).isEqualTo("음식");
        assertThat(tags.get(1).getName()).isEqualTo("abc");
        assertThat(tags.get(2).getName()).isEqualTo("ABC");


        String content2 = "#123식당?#phone#코트 #1######";
        Photo photo2 = new Photo(user, imagePath, content2);
        List<Tag> tags2 = photo2.extractTags();
        assertThat(tags2.size()).isEqualTo(4);
        assertThat(tags2.get(0).getName()).isEqualTo("123식당");
        assertThat(tags2.get(1).getName()).isEqualTo("phone");
        assertThat(tags2.get(2).getName()).isEqualTo("코트");
        assertThat(tags2.get(3).getName()).isEqualTo("1");
    }


}

