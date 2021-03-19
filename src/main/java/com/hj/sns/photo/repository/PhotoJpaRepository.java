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
//
    @EntityGraph(attributePaths = {"user"})
    Slice<Photo> findPhotoByUser(User user , Pageable pageable);


    @EntityGraph(attributePaths = {"user"})
    List<Photo> findTestPhotoByUser(User user);
//        select p from Photo p join fetch p.user
//

//    @OneToMany(mappedBy = "photo", cascade = CascadeType.ALL)
//    private List<PhotoTag> photoTags = new ArrayList<>();
//
//
//    @OneToMany(mappedBy = "photo")
//    private List<Comment> comments = new ArrayList<>();

}
