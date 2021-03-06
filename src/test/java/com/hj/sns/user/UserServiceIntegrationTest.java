package com.hj.sns.user;

import com.hj.sns.user.exception.UserAlreadyExistException;
import com.hj.sns.user.exception.UserNotFoundException;
import com.hj.sns.user.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceIntegrationTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    @DisplayName("회원을 저장한다.")
    void save() {
        User user = new User("newUser","password");
        userService.save(user);
        assertThat(user).isEqualTo(userJpaRepository.findById(user.getId()).get());

    }


    @Test
    @DisplayName("중복회원을 저장할 경우 예외가 발생한다.")
    void saveDuplicateUser() {

        User user2 =  new User("userA", "password");
        UserAlreadyExistException e = assertThrows(UserAlreadyExistException.class, () -> userService.save(user2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }


    @Test
    @DisplayName("회원을 id로 찾는다")
    void findUserById() {

        User findUser= userService.findUserById(1L);

        assertThat(findUser.getUsername()).isEqualTo("userA");

    }


    @Test
    @DisplayName("해당 id를 가진 회원이 존재하지 않을 경우 예외가 발생한다")
    void findUserByIdThrowException() {
        UserNotFoundException e = assertThrows(UserNotFoundException.class, () -> userService.findUserById(100L));
        assertThat(e.getMessage()).isEqualTo("가입하지 않은 회원입니다.");

    }

    @Test
    @DisplayName("회원을 이름으로 찾는다")
    void findUserByUsername() {

        User findUser= userService.findUserByName("userA");

        assertThat(findUser.getId()).isEqualTo(1L);

    }

    @Test
    @DisplayName("해당 id를 가진 회원이 존재하지 않을 경우 예외가 발생한다")
    void findUserByUsernameThrowException(){
        UserNotFoundException e= assertThrows(UserNotFoundException.class, ()->userService.findUserByName("user1"));
        assertThat(e.getMessage()).isEqualTo("가입하지 않은 회원입니다.");

    }


}