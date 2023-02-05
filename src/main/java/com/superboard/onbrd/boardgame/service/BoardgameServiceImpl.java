package com.superboard.onbrd.boardgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.superboard.onbrd.boardgame.dto.SearchBoardGameByRecommand;
import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.repository.BoardgameRepository;

@Service
public class BoardgameServiceImpl implements BoardGameService {
	
	@Autowired
	private BoardgameRepository boardgameRepository;
	

	@Override
	public Page<Boardgame> searchBoardgameByRecommand(SearchBoardGameByRecommand searchBoardGameByRecommand,
			Pageable pageable) {
		return boardgameRepository.searchBoardgameByRecommand(searchBoardGameByRecommand, pageable);
	}


	@Override
	public Boardgame selectBoardameDetail(Long boardgameId) {
		return boardgameRepository.selectBoardgameDetail(boardgameId);
	}


	@Override
	public Page<Boardgame> selectBoardgameList(Pageable pageable) {
		return boardgameRepository.selectBoardgameList(pageable);
	}
}
