package com.hj.sns.photo.service;

import com.hj.sns.follow.FollowService;
import com.hj.sns.photo.model.Photo;
import com.hj.sns.photo.model.dto.PhotoDto;
import com.hj.sns.photo.model.dto.Result;
import com.hj.sns.photo.repository.PhotoJpaRepository;
import com.hj.sns.photo.repository.PhotoRepository;
import com.hj.sns.tag.model.Tag;
import com.hj.sns.tag.service.TagService;
import com.hj.sns.user.model.User;
import com.hj.sns.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoService {
    private final PhotoJpaRepository photoJpaRepository;
    private final FollowService followService;
    private final PhotoRepository photoQueryRepository;
    private final UserService userService;
    private final TagService tagService;


    @Transactional
    public Long save(Long userId, String imagePath, String content) {
        User user = userService.findUserById(userId);
        Photo photo = new Photo(user, imagePath, content);
        List<Tag> tags = photo.extractTags();
        tagService.saveTags(tags);
        photo.addPhotoTags(tags);
        photoJpaRepository.save(photo);
        return photo.getId();
    }


    public Result<List<PhotoDto>> findAllPhotosOfFollowing(Long userId) {

        List<User> followings = followService.findFollowings(userId);
        List<Long> ids = toFollowingsIds(followings);
        List<Photo> photoList = findPhotosWithUserIds(ids);

        List<PhotoDto> photos = new ArrayList<>();
        photoList.forEach(p ->
            photos.add(new PhotoDto(p))
        );

        return new Result<>(photos);
}


    public List<Photo> findPhotosWithUserIds(List<Long> userIds) {
        return photoQueryRepository.findPhotoWithUserIds(userIds);
    }

    private List<Long> toFollowingsIds(List<User> followings) {
        return followings.stream()
                .map(f -> f.getId())
                .collect(Collectors.toList());
    }

}
