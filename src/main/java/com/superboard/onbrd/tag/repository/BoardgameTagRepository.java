package com.superboard.onbrd.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.superboard.onbrd.tag.entity.BoardGameTag;

@Repository
public interface BoardgameTagRepository extends JpaRepository<BoardGameTag, Long> {
	
}
