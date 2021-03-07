package com.hj.sns.photo.repository;

import com.hj.sns.photo.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoJpaRepository extends JpaRepository<Photo, Long> {
}
