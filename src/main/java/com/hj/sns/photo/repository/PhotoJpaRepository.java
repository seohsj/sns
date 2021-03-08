package com.hj.sns.photo.repository;

import com.hj.sns.photo.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhotoJpaRepository extends JpaRepository<Photo, Long> {


}
