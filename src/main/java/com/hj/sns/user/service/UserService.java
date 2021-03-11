package com.hj.sns.user.service;

import com.hj.sns.user.exception.UserAlreadyExistException;
import com.hj.sns.user.exception.UserNotFoundException;
import com.hj.sns.user.model.User;
import com.hj.sns.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;

    @Transactional
    public Long save(User user) {
        validateDuplicateUserName(user.getUsername());
        userJpaRepository.save(user);
        return user.getId();
    }


    public User findUserById(Long id) {
        Optional<User> user = userJpaRepository.findById(id);
        return user.orElseThrow(UserNotFoundException::new);
    }


    private void validateDuplicateUserName(String name) {
        Optional<User> user = userJpaRepository.findByUsername(name);
        user.ifPresent(e -> {
            throw new UserAlreadyExistException();
        });
    }


}
