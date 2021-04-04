package com.hj.sns.photoTag;

import com.hj.sns.photo.model.Photo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotoTagJpaRepository extends JpaRepository<PhotoTag, Long> {

    @Query(value = "select pt from PhotoTag pt join fetch pt.photo p join fetch pt.tag t join fetch p.user u where t.name=:tagname")
    Slice<PhotoTag> findPhotoByTag(@Param("tagname") String tagName, Pageable pageable);

    @EntityGraph(attributePaths = {"tag"})
    List<PhotoTag> findPhotoTagByPhoto(Photo photo);

}
