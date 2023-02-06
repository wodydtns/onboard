package com.superboard.onbrd.boardgame.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.superboard.onbrd.boardgame.dto.SearchBoardGameByRecommand;
import com.superboard.onbrd.boardgame.entity.Boardgame;

public interface BoardgameRepository  {
	
	public Page<Boardgame> searchBoardgameByRecommand(SearchBoardGameByRecommand searchBoardGameByRecommand,Pageable pageable );
	
	public Boardgame selectBoardgameInfo(Long boardgameId); 
	
	public Page<Boardgame> selectBoardgameList(Pageable pageable);
}
