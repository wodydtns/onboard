package com.superboard.onbrd.boardgame.service;

import org.springframework.http.ResponseEntity;

import com.superboard.onbrd.boardgame.entity.BoardGameSearchByRecommand;
import com.superboard.onbrd.boardgame.entity.Boardgame;

public interface BoardGameService {

	ResponseEntity<Boardgame> BoardGameSearchByRecommand(BoardGameSearchByRecommand boardGameSearchByRecommand) ;
}
