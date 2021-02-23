package com.hj.sns.photo;

import com.hj.sns.base.BaseTime;
import com.hj.sns.comment.Comment;
import com.hj.sns.domain.PhotoTag;
import com.hj.sns.tag.Tag;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Photo extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    @Lob
    @Column(nullable = false)
    private String imagePath;

    @Lob
    private String content;

    @OneToMany(mappedBy = "photo", cascade = CascadeType.ALL)
    private List<PhotoTag> photoTags = new ArrayList<>();
    //photo를 조회하면 항상 tag가 나와야함.
    //tag가 없는 경우도 있다
    //  cascade.persist? 응. 사진을 생성할때 tag도 저장하면 같이 저장되어야지.
    //cascade.remove? 응,,, 사진을 지울때 PhotoTag도 지워야지.(orphanremoval =true)
    //orphan removal? 응... 사진에서 photoTag를 지웠을때 photoTag를 지워야지

    @OneToMany(mappedBy = "photo")
    private List<Comment> comments = new ArrayList<>();


    public void addTags(List<PhotoTag> tags) {
        for (PhotoTag tag : tags) {
            photoTags.add(tag);
            tag.setPhoto(this);
        }
    }


}
