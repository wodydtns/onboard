package com.superboard.onbrd.boardgame.repository;

import static com.superboard.onbrd.boardgame.entity.QBoardgame.*;
import static com.superboard.onbrd.boardgame.dto.QBoardgameDetailDto.*;
import static com.superboard.onbrd.tag.entity.QTag.*;
import static com.superboard.onbrd.tag.entity.QBoardgameTag.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.boardgame.dto.BoardgameDetailDto;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagRequest;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagResponse;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagResponse.BoardGameResponse;
import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.boardgame.entity.QBoardgame;
import com.superboard.onbrd.tag.entity.QTag;
import com.superboard.onbrd.tag.entity.Tag;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardgameRepositoryImpl implements BoardgameRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<BoardgameSearchByTagResponse.BoardGameResponse> searchBoardgameByRecommand(
			BoardgameSearchByTagRequest boardgameSearchByTagRequest, Pageable pageable) {
		
		JPAQuery<BoardgameSearchByTagResponse.BoardGameResponse> query = queryFactory
				.select(Projections.fields(BoardgameSearchByTagResponse.BoardGameResponse.class, boardgame.id,
						boardgame.name, boardgame.image))
				.from(boardgameTag).join(boardgameTag.boardgame, boardgame).join(boardgameTag.tag, tag)
				.orderBy(tag.id.asc())
				.offset(pageable.getOffset()).limit(pageable.getPageSize());
		
		if(!ObjectUtils.isEmpty(boardgameSearchByTagRequest.getTagIds())) {
			query.where(tag.id.in(boardgameSearchByTagRequest.getTagIds()));
		}
		List<BoardgameSearchByTagResponse.BoardGameResponse> results = query.fetch();
			
		return new PageImpl<BoardgameSearchByTagResponse.BoardGameResponse>(results, pageable, results.size());
	}

	@Override
	public BoardgameDetailDto selectBoardgameInfo(Long boardgameId) {
		QTag tag = QTag.tag;
		BoardgameDetailDto boardgameDetail = queryFactory
				.select(Projections.constructor(BoardgameDetailDto.class, boardgame.name, boardgame.description,
						boardgame.image, boardgame.favoriteCount))
				.from(boardgame).where(boardgame.id.eq(boardgameId)).fetchOne();
		List<Tag> tagList = queryFactory.select(tag).distinct().from(boardgameTag).join(boardgameTag.tag, tag)
				.where(boardgameTag.boardgame.id.eq(boardgameId)).fetch();
		boardgameDetail.setTagList(tagList);
		return boardgameDetail;
	}

	@Override
	public Page<Boardgame> selectBoardgameList(Pageable pageable) {
		QBoardgame boardgame = QBoardgame.boardgame;
		List<Boardgame> boardgameList = queryFactory.selectFrom(boardgame).offset(pageable.getOffset())
				.limit(pageable.getPageSize()).fetch();
		return new PageImpl<Boardgame>(boardgameList, pageable, boardgameList.size());
	}

	@Override
	public Optional<Boardgame> findById(Long id) {
		return Optional.of(queryFactory.select(boardgame).from(boardgame).where(boardgame.id.eq(id)).fetchFirst());
	}
	
	

}
