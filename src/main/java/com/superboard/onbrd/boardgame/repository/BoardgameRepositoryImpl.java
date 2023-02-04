package com.superboard.onbrd.boardgame.repository;

import static com.superboard.onbrd.boardgame.entity.QBoardgame.boardgame;
import static com.superboard.onbrd.tag.entity.QBoardgameTag.boardgameTag;
import static com.superboard.onbrd.tag.entity.QTag.tag;

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
		List<Boardgame> results = queryFactory.select(boardgame)
				.from(boardgameTag)
				.join(boardgameTag.boardgame, boardgame)
				.join(boardgameTag.tag, tag).fetch();
				
				
		return new PageImpl<Boardgame>(results, pageable,results.size()); 
	}

	@Override
	public Boardgame BoardGameDetail(Long boardgameId) {
		QBoardgame boardgame = QBoardgame.boardgame;
		Boardgame boardgameDetail = queryFactory.selectFrom(boardgame).where(boardgame.id.eq(boardgameId)).fetchOne();
		return boardgameDetail;
	}

}
