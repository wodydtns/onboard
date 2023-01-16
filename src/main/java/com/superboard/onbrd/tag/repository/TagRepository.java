package com.superboard.onbrd.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.tag.entity.Tag;
public interface TagRepository extends JpaRepository<Tag, Long> {
}
