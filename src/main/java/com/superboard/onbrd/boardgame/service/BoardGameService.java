package com.superboard.onbrd.boardgame.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.dto.SearchBoardGameByRecommand;

public interface BoardGameService {

	public Page<Boardgame> searchBoardgameByRecommand(SearchBoardGameByRecommand searchBoardGameByRecommand,Pageable pageable ) ;
	
	public Boardgame selectBoardameDetail(Long boardgameId); 
	
	public Page<Boardgame> selectBoardgameList(Pageable pageable);
}
