package com.hj.sns.follow;

import com.hj.sns.follow.exception.FollowAlreadyExistException;
import com.hj.sns.follow.query.FollowQueryJpaRepository;
import com.hj.sns.user.model.User;
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
        List<Follow> followings = followJpaRepository.findFollowByWho_Id(userId);
        return followings.stream().map(f -> f.getWhom()).collect(Collectors.toList());
    }

    @Transactional
    public void follow(Long whoId, Long whomId) {
        User who = userService.findUserById(whoId);
        User whom = userService.findUserById(whomId);
        //이미 follow한 경우
        followJpaRepository.findByWho_IdAndWhom_Id(whoId, whomId)
                .ifPresent(f -> {
                    throw new FollowAlreadyExistException();
                });
        //그렇지 않다면 who가 whom을 follow하도록
        followJpaRepository.save(new Follow(who, whom));
        /*ToDo: push*/

    }

}
