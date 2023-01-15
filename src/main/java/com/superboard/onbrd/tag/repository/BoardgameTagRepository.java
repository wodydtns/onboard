package com.superboard.onbrd.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.tag.entity.BoardgameTag;

public interface BoardgameTagRepository extends JpaRepository<BoardgameTag, Long> {
}
