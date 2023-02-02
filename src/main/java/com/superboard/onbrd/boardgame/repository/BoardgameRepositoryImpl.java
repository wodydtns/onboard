package com.superboard.onbrd.boardgame.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.entity.QBoardgame;
import com.superboard.onbrd.boardgame.dto.BoardGameSearchByRecommand;

import com.superboard.onbrd.tag.entity.QTag;

import lombok.RequiredArgsConstructor;

import com.superboard.onbrd.tag.entity.QBoardgameTag;

@Repository
@RequiredArgsConstructor
public class BoardgameRepositoryImpl implements BoardgameRepository {

	private final JPAQueryFactory queryFactory;
	
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
		*/
		List<Boardgame> results = queryFactory.selectFrom(boardgame)
				.join(boardgame.boardgameTags).fetchJoin().join(tag.tag_id).fetch();
				
		return new PageImpl<Boardgame>(results, pageable,results.size()); 
	}

}
