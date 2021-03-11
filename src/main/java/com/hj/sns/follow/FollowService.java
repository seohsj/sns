package com.hj.sns.follow;

import com.hj.sns.follow.exception.FollowAlreadyExistException;
import com.hj.sns.user.exception.UserNotFoundException;
import com.hj.sns.user.model.User;
import com.hj.sns.user.repository.UserJpaRepository;
import com.hj.sns.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {
    private final FollowJpaRepository followJpaRepository;
    private final UserService userService;

    public List<User> findFollowings(Long userId) {
        List<Follow> followings = followJpaRepository.findFollowings(userId);
        return followings.stream().map(f -> f.getWhom()).collect(Collectors.toList());
    }
    @Transactional
    public void follow(Long whoId, Long WhomId) {
        User who = userService.findUserById(whoId);
        User whom = userService.findUserById(WhomId);
        //이미 follow한 경우
        followJpaRepository.findByWhoAndWhom(who, whom)
                .ifPresent(f -> {
                    throw new FollowAlreadyExistException();
                });
        //그렇지 않다면 who가 whom을 follow하도록
        Follow follow = new Follow(who, whom);
        followJpaRepository.save(follow);
        /*ToDo: push*/

    }

}
