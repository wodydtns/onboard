package com.superboard.onbrd.boardgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.boardgame.entity.NonSearchClickLog;

public interface BoardNonSearchClickLogRepository extends JpaRepository<NonSearchClickLog, Long>{

	NonSearchClickLog findByBoardgameId(Long boardGameId);
	
}
