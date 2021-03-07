package com.hj.sns.tag.repository;

import com.hj.sns.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagJpaRepository extends JpaRepository<Tag, Long> {

    public Optional<Tag> findByName(String name);
}
