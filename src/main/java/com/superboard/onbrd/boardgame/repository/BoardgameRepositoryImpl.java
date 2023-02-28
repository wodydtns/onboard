package com.superboard.onbrd.boardgame.repository;

import static com.superboard.onbrd.boardgame.entity.QBoardgame.*;
import static com.superboard.onbrd.boardgame.dto.QBoardgameDetailDto.*;
import static com.superboard.onbrd.boardgame.entity.QBoardgameClickLog.*;
import static com.superboard.onbrd.boardgame.entity.QNonSearchClickLog.*;
import static com.superboard.onbrd.tag.entity.QTag.*;
import static com.superboard.onbrd.tag.entity.QBoardgameTag.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.boardgame.dto.BoardgameDetailDto;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagRequest;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagResponse;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagResponse.BoardGameResponse;
import com.superboard.onbrd.boardgame.dto.RecommandBoardgameDto;
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
	public Optional<Boardgame> findById(Long id) {
		return Optional.of(queryFactory.select(boardgame).from(boardgame).where(boardgame.id.eq(id)).fetchFirst());
	}

	@Override
	@Transactional(readOnly = true)
	public Long updateFavoriteCount(Long id) {
		long count = queryFactory.update(boardgame)
		.where(boardgame.id.eq(id))
		.set(boardgame.favoriteCount, boardgame.favoriteCount.add(1)).execute();
		return count; 
	}

	@Override
	public Page<BoardgameSearchByTagResponse.BoardGameResponse> selectRecommandBoardgameList(Pageable pageable) {
		LocalDateTime startDate =LocalDateTime.now().minusDays(30);
		
		List<BoardgameSearchByTagResponse.BoardGameResponse> recommandBoardgameList = queryFactory.select(Projections.fields(BoardgameSearchByTagResponse.BoardGameResponse.class, boardgame.id,boardgame.name,boardgame.image))
		.from(nonSearchClickLog).join(nonSearchClickLog.boardgame  ,boardgame )
		.where(nonSearchClickLog.clickAt.after(startDate))
		.orderBy(boardgame.clickCount.desc())
		.limit(pageable.getPageSize()).fetch();
		return new PageImpl<BoardgameSearchByTagResponse.BoardGameResponse>(recommandBoardgameList, pageable, recommandBoardgameList.size());
	}

	@Override
	@Transactional(readOnly = true)
	public void updateClickCount(Long id) {
		queryFactory.update(boardgame).where(boardgame.id.eq(id))
		.set(boardgame.clickCount, boardgame.clickCount.add(1)).execute();
	}

}

