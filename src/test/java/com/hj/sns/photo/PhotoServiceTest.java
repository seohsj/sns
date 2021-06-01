package com.hj.sns.photo;

import com.hj.sns.photo.exception.PhotoNotFoundException;
import com.hj.sns.photo.model.Photo;
import com.hj.sns.user.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhotoServiceTest {
    @InjectMocks
    private PhotoService photoService;
    @Mock
    private PhotoJpaRepository photoJpaRepository;

    @Test
    @DisplayName("id로 photo를 찾는다")
    void findPhotoById() {
        User user = new User("userA", "password");
        when(photoJpaRepository.findById(1L))
                .thenReturn(Optional.of(new Photo(user, "imagePath", "content")));
        Photo photo = photoService.findPhotoById(1L);
        assertThat(photo.getImagePath()).isEqualTo("imagePath");
        assertThat(photo.getContent()).isEqualTo("content");
    }

    @Test
    @DisplayName("해당 id의 photo가 없을 경우 예외를 발생시킨다")
    void findPhotoByIdFail() {
        when(photoJpaRepository.findById(1L)).thenReturn(Optional.empty());
        PhotoNotFoundException e = assertThrows(PhotoNotFoundException.class, () -> photoService.findPhotoById(1L));
        assertThat(e.getMessage()).isEqualTo("해당 사진이 존재하지 않습니다.");
    }
}
