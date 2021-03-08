package com.hj.sns.follow;

import com.hj.sns.user.model.User;
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

    public List<User> findFollowings(Long userId){
        List<Follow> followings = followJpaRepository.findFollowings(userId);
        return followings.stream().map(f->f.getWhom()).collect(Collectors.toList());
    }

}
