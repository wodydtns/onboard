package com.superboard.onbrd.boardgame.repository;

import static com.superboard.onbrd.boardgame.entity.QBoardgame.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.boardgame.dto.SearchBoardGameByRecommand;
import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.entity.QBoardgame;
import com.superboard.onbrd.tag.entity.QBoardgameTag;
import com.superboard.onbrd.tag.entity.QTag;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardgameRepositoryImpl implements BoardgameRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Boardgame> searchBoardgameByRecommand(SearchBoardGameByRecommand searchBoardGameByRecommand,
		Pageable pageable) {

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
				.join(boardgameTag.tag, tag)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
					
		return new PageImpl<Boardgame>(results, pageable,results.size()); 
	}

	@Override
	public Boardgame selectBoardgameInfo(Long boardgameId) {
		QBoardgame boardgame = QBoardgame.boardgame;
		Boardgame boardgameDetail = queryFactory.selectFrom(boardgame).where(boardgame.id.eq(boardgameId))
				.fetchOne();
		return boardgameDetail;
	}

	@Override
	public Page<Boardgame> selectBoardgameList(Pageable pageable) {
		QBoardgame boardgame = QBoardgame.boardgame;
		List<Boardgame> boardgameList = queryFactory.selectFrom(boardgame)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		return new PageImpl<Boardgame>(boardgameList, pageable,boardgameList.size());
	}

	@Override
	public Optional<Boardgame> findById(Long id) {
		return Optional.of(
			queryFactory
				.select(boardgame)
				.from(boardgame)
				.where(boardgame.id.eq(id))
				.fetchFirst());
	}

}
