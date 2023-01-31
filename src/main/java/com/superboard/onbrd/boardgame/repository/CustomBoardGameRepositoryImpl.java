package com.superboard.onbrd.boardgame.repository;

import static com.superboard.onbrd.boardgame.entity.QBoardgame.*;
import static com.superboard.onbrd.tag.entity.QBoardgameTag.*;
import static com.superboard.onbrd.tag.entity.QTag.*;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagRequest;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagResponse;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomBoardGameRepositoryImpl implements CustomBoardGameRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public BoardgameSearchByTagResponse searchBoardgamesByTag(BoardgameSearchByTagRequest params) {
		List<BoardgameSearchByTagResponse.BoardGameResponse> responses = queryFactory
			.select(Projections.fields(BoardgameSearchByTagResponse.BoardGameResponse.class,
				boardgame.id,
				boardgame.name,
				boardgame.image
			))
			.from(boardgameTag)
			.join(boardgameTag.boardgame, boardgame)
			.join(boardgameTag.tag, tag)
			.where(tag.id.in(params.getTagIds()))
			.orderBy(params.getOrderBy().getOrderSpecifiers())
			.offset(params.getOffset())
			.limit(params.getLimit() + 1)
			.fetch();

		Boolean hasNext = getHasNext(responses, params.getLimit());

		return new BoardgameSearchByTagResponse(hasNext, responses);
	}

	private Boolean getHasNext(List<?> responses, int limit) {
		if (responses.size() > limit) {
			responses.remove(limit);
			return true;
		}

		return false;
	}
}
