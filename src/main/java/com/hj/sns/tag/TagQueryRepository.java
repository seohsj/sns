package com.hj.sns.tag;

import com.hj.sns.tag.model.Tag;
import com.hj.sns.tag.model.dto.TagNameSearchDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagQueryRepository extends JpaRepository<Tag, Long> {

//    select t.tag_id, t.name, count(photo_tag.tag_id )
//    from Tag t left join photo_tag on t.tag_id= photo_tag.tag_id
//    where t.name like 'tag%'
//    group by t.tag_id, t.name;


    @Query(value= "select new com.hj.sns.tag.model.dto.TagNameSearchDto(t.id, t.name, count(pt.id)) from Tag t left join t.photoTags pt where t.name like :name% group by t.id, t.name")
    Slice<TagNameSearchDto> searchTag(@Param("name") String name, Pageable pageable);
}
