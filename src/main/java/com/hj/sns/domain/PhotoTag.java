package com.hj.sns.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PhotoTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name="PHOTO_ID")
    private Photo photo;

    @ManyToOne
    @JoinColumn(name="TAG_ID")
    private Tag tag;
}
