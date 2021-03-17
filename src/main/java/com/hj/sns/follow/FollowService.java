package com.hj.sns.follow;

import com.hj.sns.follow.dto.FollowerDto;
import com.hj.sns.follow.dto.FollowingDto;
import com.hj.sns.follow.exception.FollowAlreadyExistException;

import com.hj.sns.follow.exception.FollowNotFoundException;
import com.hj.sns.user.User;
import com.hj.sns.user.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    public Slice<FollowingDto> findFollowingsPaging(String username, Pageable pageable) {
        User user = userService.findUserByName(username);
        return followJpaRepository.pagingFindByWho(user.getId(), pageable)
                .map(FollowingDto::new);
    }

    public Slice<FollowerDto> findFollowerPaging(String username, Pageable pageable) {
        User user = userService.findUserByName(username);
        return followJpaRepository.pagingFindByWhom(user.getId(), pageable)
                .map(FollowerDto::new);
    }

    @Transactional
    public void follow(String whoName, String whomName) {
        User who = userService.findUserByName(whoName);
        User whom = userService.findUserByName(whomName);
        //이미 follow한 경우
        followJpaRepository.findByWho_IdAndWhom_Id(who.getId(), whom.getId())
                .ifPresent(f -> {
                    throw new FollowAlreadyExistException();
                });
        //그렇지 않다면 who가 whom을 follow하도록
        followJpaRepository.save(new Follow(who, whom));
        /*ToDo: push*/

    }

    
    @Transactional
    public void unfollow(String whoName, String whomName) {
        User who = userService.findUserByName(whoName);
        User whom = userService.findUserByName(whomName);
        Follow follow = followJpaRepository.findByWho_IdAndWhom_Id(who.getId(), whom.getId())
                .orElseThrow(() -> new FollowNotFoundException());
        followJpaRepository.delete(follow);
    }

}
