package com.superboard.onbrd.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.tag.entity.FavoriteTag;

public interface FavoriteTagRepository extends JpaRepository<FavoriteTag, Long> {
}
