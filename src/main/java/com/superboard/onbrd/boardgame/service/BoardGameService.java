package com.superboard.onbrd.boardgame.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.dto.BoardGameSearchByRecommand;

public interface BoardGameService {

	public Page<Boardgame> BoardGameSearchByRecommand(BoardGameSearchByRecommand boardGameSearchByRecommand,Pageable pageable ) ;
	
	public Boardgame BoardGameDetail(Long boardgameId); 
}
