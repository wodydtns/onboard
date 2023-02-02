package com.superboard.onbrd.boardgame.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.util.ArrayBuilders.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.entity.QBoardgame;
import com.superboard.onbrd.boardgame.dto.BoardGameSearchByRecommand;

import com.superboard.onbrd.tag.entity.QTag;
import com.superboard.onbrd.tag.entity.QBoardgameTag;

@Repository
public class BoardgameRepositoryImpl implements BoardgameRepository {

	private final JPAQueryFactory queryFactory;
	
	public BoardgameRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}
	
	@Override
	public Page<Boardgame> BoardGameSearchByRecommand(BoardGameSearchByRecommand boardGameSearchByRecommand,Pageable pageable ) {
		
		QBoardgame boardgame = QBoardgame.boardgame;
		QTag tag = QTag.tag;
		QBoardgameTag boardgameTag = QBoardgameTag.boardgameTag; 
		/*
		
		BooleanBuilder builder = new BooleanBuilder();
		if(StringUtils.hasText(boardGameSearchByRecommand.getTagName())) {
			builder.and(boardgame.)
		}
		
		List<Tuple> results = queryFactory.select(new QBoardgame(boardgame.id,boardgame.name,boardgame.description,boardgame.image)).from(boardgame)
				.from(boardgame).innerJoin(boardgame.boardgameTags, boardgameTag).fetch();
				*/
		return null;
	}

}
