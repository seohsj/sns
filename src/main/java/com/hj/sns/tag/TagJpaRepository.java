package com.hj.sns.tag;

import com.hj.sns.tag.model.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagJpaRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);
//    select tag.tag_id from Tag left join photo_tag on photo_tag.tag_id= tag.tag_id  group by tag.tag_id having count(photo_tag_id)=10
    @Query(value = "select t.id from Tag t left join t.photoTags pt where t.id in (:tagIds) group by t.id having count(pt.id)=0")
    List<Long> getOrphanTagId(@Param("tagIds") List<Long> tagId);
//
//    Slice<Tag> findByNameStartsWith(String name, Pageable pageable);



    //Query(value= "select t from Tag t where (
}
