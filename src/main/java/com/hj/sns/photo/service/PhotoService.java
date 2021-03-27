package com.hj.sns.photo.service;

import com.hj.sns.follow.FollowService;
import com.hj.sns.photo.exception.PhotoNotFoundException;
import com.hj.sns.photo.model.Photo;
import com.hj.sns.photo.model.dto.PhotoDto;
import com.hj.sns.photo.model.dto.PhotoFeedDto;
import com.hj.sns.photo.repository.PhotoJpaRepository;
import com.hj.sns.tag.model.Tag;
import com.hj.sns.tag.repository.TagJpaRepository;
import com.hj.sns.user.User;
import com.hj.sns.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoService {
    private final PhotoJpaRepository photoJpaRepository;
    private final UserService userService;
    private final TagJpaRepository tagJpaRepository;
    private final FollowService followService;

    @Transactional
    public Long save(Long userId, String imagePath, String content) {
        User user = userService.findUserById(userId);
        Photo photo = new Photo(user, imagePath, content);

        List<Tag> tags = extractTags(photo);
        photo.addPhotoTags(tags);

        List<User> users = extractMentionedUsers(photo);
        photo.addMentionedUsers(users);

        photoJpaRepository.save(photo);
        return photo.getId();
    }


    @Transactional
    public Long updatePhoto(Long photoId, String imagePath, String content) {
        Photo photo = findPhotoById(photoId);
        if (imagePath != null) {
            photo.updateImagePath(imagePath);
        }
        if (content != null) {
            photo.updateContent(content);
            List<Tag> tags = extractTags(photo);
            photo.updatePhotoTags(tags);

            List<User> users = extractMentionedUsers(photo);
            photo.updateMentionedUsers(users);

        }
        return photo.getId();

    }

    @Transactional
    public void deletePhoto(Long photoId) {
        Photo photo = findPhotoById(photoId);
        photoJpaRepository.delete(photo);
    }

    public Photo findPhotoById(Long id) {
        return photoJpaRepository.findById(id)
                .orElseThrow(PhotoNotFoundException::new);
    }

    public Slice<PhotoDto> findPhotoByUser(String username, Pageable pageable) {
        User user = userService.findUserByName(username);
        Slice<Photo> photos = photoJpaRepository.findPhotoByUser(user, pageable);
        return photos.map(PhotoDto::new);
    }

    public Slice<PhotoFeedDto> getUserFeed(String username, Pageable pageable) {
        List<Long> ids = new ArrayList<>();
        User user = userService.findUserByName(username);
        ids.add(user.getId());

        List<User> followings = followService.findFollowings(user);
        for (User f : followings) {
            ids.add(f.getId());
        }

        return photoJpaRepository.findFeedByUser(ids, pageable).map(PhotoFeedDto::new);
    }

    private List<Tag> extractTags(Photo photo) {
        List<String> tagNames = photo.extractTags();
        return getTags(tagNames);
    }

    private List<Tag> getTags(List<String> tagNames) {
        return tagNames.stream().map(
                tagName -> tagJpaRepository.findByName(tagName)
                        .orElseGet(() -> tagJpaRepository.save(new Tag(tagName))
                        ))
                .distinct()
                .collect(Collectors.toList());
    }

    private List<User> extractMentionedUsers(Photo photo) {
        List<String> userNames = photo.extractMentionedUsers();
        return getUsers(userNames);

    }

    private List<User> getUsers(List<String> userNames) {
        return userNames.stream().map(userService::findUserByName)
                .distinct()
                .collect(Collectors.toList());

    }


}
