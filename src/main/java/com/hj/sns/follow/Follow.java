package com.hj.sns.follow;

import com.hj.sns.user.model.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "who_id")
    private User who;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "whom_id")
    private User whom;
    protected Follow(){}


    public Follow(User who,User whom){
        this.who=who;
        this.whom=whom;
    }




}
