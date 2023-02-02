package com.superboard.onbrd.boardgame.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.entity.QBoardgame;
import com.superboard.onbrd.boardgame.dto.BoardGameSearchByRecommand;


@Repository
public class BoardgameRepositoryImpl implements BoardgameRepository {

	private final JPAQueryFactory queryFactory;
	
	public BoardgameRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}
	
	@Override
	public Page<Boardgame> BoardGameSearchByRecommand(BoardGameSearchByRecommand boardGameSearchByRecommand) {
		return null;
	}

}
