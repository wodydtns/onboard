package com.superboard.onbrd.boardgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.boardgame.entity.SearchClickLog;

public interface BoardSearchClickLogRepository extends JpaRepository<SearchClickLog, Long>{

	SearchClickLog findByBoardgameId(Long boardgameId);
	
}
