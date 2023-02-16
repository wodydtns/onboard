package com.superboard.onbrd.boardgame.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.superboard.onbrd.boardgame.dto.BoardgameDetailDto;
import com.superboard.onbrd.boardgame.dto.SearchBoardGameByRecommand;
import com.superboard.onbrd.boardgame.entity.Boardgame;

public interface BoardGameService {
	public Page<Boardgame> searchBoardgameByRecommand(SearchBoardGameByRecommand searchBoardGameByRecommand,Pageable pageable ) ;
	
	public BoardgameDetailDto selectBoardgameInfo(Long boardgameId); 
  
	public Page<Boardgame> selectBoardgameList(Pageable pageable);

	Boardgame findVerifiedOneById(Long id);
}
