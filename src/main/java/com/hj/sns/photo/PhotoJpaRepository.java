package com.hj.sns.photo;

import com.hj.sns.photo.model.Photo;
import com.hj.sns.user.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotoJpaRepository extends JpaRepository<Photo, Long> {
    Slice<Photo> findPhotoByUser(User user , Pageable pageable);

    @Query("select p from Photo p join fetch p.user where p.user.id in (:userIds)")
    Slice<Photo> findFeedByUser(@Param("userIds") List<Long> userIds, Pageable pageable);

}
