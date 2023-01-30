package com.superboard.onbrd.boardgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.repository.BoardgameRepository;

@Service
public class BoardgameServiceImpl implements BoardGameService {
	

	@Override
	public ResponseEntity<Boardgame> BoardGameSearchByRecommand(
			com.superboard.onbrd.boardgame.entity.BoardGameSearchByRecommand boardGameSearchByRecommand) {
		// TODO Auto-generated method stub
		return null;
	}
}
