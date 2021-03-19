package com.hj.sns.photo.repository;

import com.hj.sns.comment.model.Comment;
import com.hj.sns.photo.model.Photo;
import com.hj.sns.photo.model.PhotoTag;
import com.hj.sns.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public interface PhotoJpaRepository extends JpaRepository<Photo, Long> {
    Slice<Photo> findPhotoByUser(User user , Pageable pageable);


}
