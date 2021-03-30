package com.hj.sns.tag.repository;

import com.hj.sns.tag.model.PhotoTag;

import com.hj.sns.tag.model.dto.TagSearchDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhotoTagQueryRepository extends JpaRepository<PhotoTag, Long> {

    @Query(value = "select new com.hj.sns.tag.model.dto.TagSearchDto(p.id, p.imagePath, p.content, u.username, p.createdDate, p.lastModifiedDate) " +
            "from PhotoTag pt " +
            "left join pt.photo p " +
            "left join pt.tag t " +
            "left join p.user u " +
            "where t.name=:tagname ")
    Slice<TagSearchDto> findTagSearchResult(@Param("tagname") String tagName, Pageable pageable);

}
