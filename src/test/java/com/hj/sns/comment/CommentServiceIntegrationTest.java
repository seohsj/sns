package com.hj.sns.comment;

import com.hj.sns.comment.exception.CommentNotFoundException;
import com.hj.sns.comment.model.Comment;
//import com.hj.sns.comment.model.CommentUser;
import com.hj.sns.photo.service.PhotoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceIntegrationTest {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PhotoService photoService;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("댓글 작성")
    void writeComment() {
        commentService.writeComment(6L, 1L, "comment");
        commentService.writeComment(6L, 2L, "comment");
        em.flush();
        em.clear();
        List<Comment> comments = photoService.findPhotoById(6L).getComments();
        assertThat(comments.size()).isEqualTo(2);
        assertTrue(comments.stream().allMatch(c -> (c.getUser().getId().equals(1L) || c.getUser().getId().equals(2L))));
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() {
        commentService.deleteComment(1L);
        assertThrows(CommentNotFoundException.class, () -> commentService.findCommentById(1L));

        commentService.deleteComment(2L);
        assertThrows(CommentNotFoundException.class, () -> commentService.findCommentById(2L));
    }

}