package com.hj.sns.domain;

import com.hj.sns.photo.Photo;
import com.hj.sns.tag.Tag;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Table(name="photo_tag")
public class PhotoTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="photo_tag_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="photo_id")
    private Photo photo;

    @ManyToOne(fetch = FetchType.LAZY) //강의 참고
    @JoinColumn(name="tag_id")
    private Tag tag;

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
