package com.hj.sns.user.service;

import com.hj.sns.user.exception.UserAlreadyExistException;
import com.hj.sns.user.model.User;
import com.hj.sns.user.repository.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired private UserService userService;

    @Autowired private UserJpaRepository userJpaRepository;

    @Test
    @DisplayName("회원을 저장한다.")
    void save(){
        User user= new User("userA","191823");
        userService.save(user);
        assertThat(user).isEqualTo(userJpaRepository.findById(user.getId()).get());



    }


    @Test
    @DisplayName("중복회원을 저장할 경우 예외가 발생한다.")
    void saveDuplicateUser(){
        User user1= new User("userA","191823");
        userService.save(user1);

        User user2=new User("userA","aaaaaaa");
        UserAlreadyExistException e = assertThrows(UserAlreadyExistException.class, () -> userService.save(user2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }





}