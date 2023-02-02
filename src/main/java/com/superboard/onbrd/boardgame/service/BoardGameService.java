package com.superboard.onbrd.boardgame.service;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.dto.BoardGameSearchByRecommand;

public interface BoardGameService {

	Page<Boardgame> BoardGameSearchByRecommand(BoardGameSearchByRecommand boardGameSearchByRecommand,Pageable pageable ) ;
}
