package com.hj.sns.comment;

import com.hj.sns.comment.exception.CommentNotFoundException;
import com.hj.sns.comment.model.Comment;
import com.hj.sns.photo.model.Photo;
import com.hj.sns.photo.service.PhotoService;
import com.hj.sns.user.User;
import com.hj.sns.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final UserService userService;
    private final PhotoService photoService;
    private final CommentJpaRepository commentJpaRepository;

    @Transactional
    public Long writeComment(Long photoId, Long userId, String content) {
        User user = userService.findUserById(userId);
        Photo photo = photoService.findPhotoById(photoId);
        Comment comment = new Comment(user, content, photo);
        List<String> userNames = comment.extractMentionedUsers();
        for (String userName : userNames) {
            User mentionedUser= userService.findUserByName(userName);
            comment.addMentionedUser(mentionedUser);
        }
        commentJpaRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment= findCommentById(commentId);
        commentJpaRepository.delete(comment);
    }

    public Comment findCommentById(Long commentId) {
        return commentJpaRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }


}
