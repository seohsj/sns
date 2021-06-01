package com.hj.sns.user;

import com.hj.sns.user.exception.UserAlreadyExistException;
import com.hj.sns.user.exception.UserNotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Test
    @DisplayName("회원을 저장한다.")
    void save() {
        userService.save(new User("userA","password"));
        verify(userJpaRepository, times(1)).save(any(User.class));

    }


    @Test
    @DisplayName("중복회원을 저장할 경우 예외가 발생한다.")
    void saveDuplicateUser() {
        when(userJpaRepository.findByUsername("userA")).thenReturn(Optional.of(new User("userA","password")));
        UserAlreadyExistException e = assertThrows(UserAlreadyExistException.class, () -> userService.save(new User("userA", "password")));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }


    @Test
    @DisplayName("회원을 id로 찾는다")
    void findUserById() {
        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(new User("userA", "password")));
        User findUser = userService.findUserById(1L);
        assertThat(findUser.getUsername()).isEqualTo("userA");
        assertThat(findUser.getPassword()).isEqualTo("password");


    }


    @Test
    @DisplayName("해당 id를 가진 회원이 존재하지 않을 경우 예외가 발생한다")
    void findUserByIdThrowException() {
        when(userJpaRepository.findById(1L)).thenReturn(Optional.empty());
        UserNotFoundException e = assertThrows(UserNotFoundException.class, () -> userService.findUserById(1L));
        assertThat(e.getMessage()).isEqualTo("가입하지 않은 회원입니다.");
    }

}
