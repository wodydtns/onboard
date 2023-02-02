package com.superboard.onbrd.boardgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.superboard.onbrd.boardgame.dto.BoardGameSearchByRecommand;
import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.repository.BoardgameRepository;

@Service
public class BoardgameServiceImpl implements BoardGameService {
	
	@Autowired
	private BoardgameRepository boardgameRepository;
	
	@Override
	public Page<Boardgame> BoardGameSearchByRecommand(BoardGameSearchByRecommand boardGameSearchByRecommand) {
			
		return boardgameRepository.BoardGameSearchByRecommand(boardGameSearchByRecommand);
	}
}
