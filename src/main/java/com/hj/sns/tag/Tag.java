package com.hj.sns.tag;

import com.hj.sns.domain.PhotoTag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tag_id")
    private Long id;

    @Column(nullable = false)
    private String tag;

    @OneToMany(mappedBy = "tag")
    private List<PhotoTag> photoTags=new ArrayList<>();
    }
