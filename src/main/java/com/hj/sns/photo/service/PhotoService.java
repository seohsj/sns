package com.hj.sns.photo.service;

import com.hj.sns.photo.model.Photo;
import com.hj.sns.photo.model.dto.PhotoDto;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoService {
    private final PhotoJpaRepository photoJpaRepository;
    private final UserService userService;
    private final TagJpaRepository tagJpaRepository;


    @Transactional
    public Long save(Long userId, String imagePath, String content) {
        User user = userService.findUserById(userId);
        Photo photo = new Photo(user, imagePath, content);
        List<Tag> tags = photo.extractTags();

        List<Tag> collect = tags.stream().map(
                tag -> tagJpaRepository.findByName(
                        tag.getName()).orElseGet(() -> tagJpaRepository.save(tag)
                )).collect(Collectors.toList());

        photo.addPhotoTags(collect);
        photoJpaRepository.save(photo);
        return photo.getId();
    }

    public Slice<PhotoDto> findPhotoByUser(String username, Pageable pageable) {
        User user = userService.findUserByName(username);
        Slice<Photo> photos = photoJpaRepository.findPhotoByUser(user, pageable);
        return photos.map(PhotoDto::new);
    }

}
