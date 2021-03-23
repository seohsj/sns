package com.hj.sns.comment;

import com.hj.sns.comment.model.Comment;
import com.hj.sns.photo.repository.PhotoJpaRepository;
import com.hj.sns.photo.service.PhotoService;
import com.hj.sns.user.User;
import com.hj.sns.user.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class CommentServiceIntegrationTest {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private CommentJpaRepository commentJpaRepository;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("댓글 작성")
    void writeComment() {
        User user = new User("UserA", "password");
        User user2 = new User("UserB", "password");
        userJpaRepository.save(user);
        userJpaRepository.save(user2);

        Long id = photoService.save(user.getId(), "imagePath", "photo");

        em.flush();
        em.clear();
        commentService.writeComment(id, user.getId(), "comment");
        commentService.writeComment(id, user2.getId(), "comment");
        List<Comment> comments = photoService.findPhotoById(id).getComments();
        assertThat(comments.size()).isEqualTo(2);
        assertTrue(comments.stream().allMatch(c -> (c.getUser().equals(user) || c.getUser().equals(user2))));
    }

}