package com.superboard.onbrd.boardgame.repository;

import java.util.List;

import org.springframework.data.domain.Page;

import com.superboard.onbrd.boardgame.dto.BoardGameSearchByRecommand;
import com.superboard.onbrd.boardgame.entity.Boardgame;

public interface BoardgameRepository  {
	
	Page<Boardgame> BoardGameSearchByRecommand(BoardGameSearchByRecommand boardGameSearchByRecommand);
}
