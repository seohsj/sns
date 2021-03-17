package com.hj.sns.photo.repository;

import com.hj.sns.photo.model.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PhotoRepository {
    private final EntityManager em;

    public List<Photo> findPhotoWithUserIds(List<Long> userIds) {
        return em.createQuery("select p from Photo p join fetch p.user u where u.id in :userIds",Photo.class)
                .setParameter("userIds",userIds)
                .getResultList();
    }
}
