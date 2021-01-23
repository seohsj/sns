package com.hj.sns.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="WHO_ID")
    private User who;

    @ManyToOne
    @JoinColumn(name="WHOM_ID")
    private User whom;
}
