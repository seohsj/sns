package com.hj.sns.photo.model;

import com.hj.sns.tag.model.Tag;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "photo_tag")
public class PhotoTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_tag_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @ManyToOne(fetch = FetchType.LAZY) //강의 참고
    @JoinColumn(name = "tag_id")
    private Tag tag;


    protected PhotoTag() {
    }

    public PhotoTag(Photo photo, Tag tag) {
        this.photo = photo;
        this.tag = tag;
    }



}
