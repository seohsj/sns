package com.hj.sns.photoTag;

import com.hj.sns.photoTag.model.PhotoTag;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhotoTagJpaRepository extends JpaRepository<PhotoTag, Long> {

    @Query(value="select pt from PhotoTag pt join fetch pt.photo p join fetch pt.tag t join fetch p.user u where t.name=:tagname")
    Slice<PhotoTag> findTagSearchResult(@Param("tagname") String tagName, Pageable pageable);
}
