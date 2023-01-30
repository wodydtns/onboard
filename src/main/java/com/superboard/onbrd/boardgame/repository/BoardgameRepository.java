package com.superboard.onbrd.boardgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.superboard.onbrd.boardgame.entity.Boardgame;

@Repository
public interface BoardgameRepository extends JpaRepository<Boardgame, Long> {
	
	
	
}
