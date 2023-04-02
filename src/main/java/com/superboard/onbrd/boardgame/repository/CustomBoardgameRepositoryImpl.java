package com.superboard.onbrd.boardgame.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.boardgame.dto.BoardgameDetailDto;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagRequest;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagResponse;
import com.superboard.onbrd.boardgame.dto.TopBoardgameDto;
import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.tag.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.superboard.onbrd.boardgame.entity.QBoardgame.boardgame;
import static com.superboard.onbrd.boardgame.entity.QNonSearchClickLog.nonSearchClickLog;
import static com.superboard.onbrd.boardgame.entity.QSearchClickLog.searchClickLog;
import static com.superboard.onbrd.tag.entity.QBoardgameTag.boardgameTag;
import static com.superboard.onbrd.tag.entity.QTag.tag;

@Repository
@RequiredArgsConstructor
public class CustomBoardgameRepositoryImpl implements CustomBoardgameRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<BoardgameSearchByTagResponse.BoardGameResponse> searchBoardgameList(BoardgameSearchByTagRequest boardgameSearchByTagRequest) {
		
		JPAQuery<BoardgameSearchByTagResponse.BoardGameResponse> query = queryFactory
				.select(Projections.fields(BoardgameSearchByTagResponse.BoardGameResponse.class, boardgame.id,
						boardgame.name, boardgame.image))
				.from(boardgameTag).join(boardgameTag.boardgame, boardgame).join(boardgameTag.tag, tag)
				.where(boardgameNameLike(boardgameSearchByTagRequest.getName()))
				.orderBy(tag.id.asc())
				.offset(boardgameSearchByTagRequest.getOffset()).limit(boardgameSearchByTagRequest.getLimit());
		
		if(!ObjectUtils.isEmpty(boardgameSearchByTagRequest.getTagIds())) {
			query.where(tag.id.in(boardgameSearchByTagRequest.getTagIds()));
		}

		List<BoardgameSearchByTagResponse.BoardGameResponse> results = query.fetch();
			
		return results;
	}

	private BooleanExpression tagIsIn(List<Long> tagIds){
		return tagIds.isEmpty() ? null : tag.id.in(tagIds);
	}

	private BooleanExpression boardgameNameLike(String boardgameName){
		return StringUtils.hasText(boardgameName) ? null : boardgame.name.like(boardgameName);
	}

	@Override
	public BoardgameDetailDto selectBoardgameInfo(Long boardgameId) {
		BoardgameDetailDto boardgameDetail = queryFactory
				.select(Projections.constructor(BoardgameDetailDto.class, boardgame.id,boardgame.name, boardgame.description,
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
	public List<BoardgameSearchByTagResponse.BoardGameResponse> selectRecommandBoardgameList(BoardgameSearchByTagRequest boardgameSearchByTagRequest) {
		LocalDateTime startDate =LocalDateTime.now().minusDays(30);
		
		List<BoardgameSearchByTagResponse.BoardGameResponse> recommandBoardgameList = queryFactory.select(Projections.fields(BoardgameSearchByTagResponse.BoardGameResponse.class, boardgame.id,boardgame.name,boardgame.image))
		.from(nonSearchClickLog).join(nonSearchClickLog.boardgame  ,boardgame )
		.where(nonSearchClickLog.clickAt.after(startDate))
		.orderBy(boardgame.clickCount.desc())
		.limit(boardgameSearchByTagRequest.getLimit()).fetch();
		return recommandBoardgameList;
	}

	@Override
	@Transactional(readOnly = true)
	public void updateClickCount(Long id) {
		queryFactory.update(boardgame).where(boardgame.id.eq(id))
		.set(boardgame.clickCount, boardgame.clickCount.add(1)).execute();
	}

	@Override
	public List<TopBoardgameDto> selectTop10BoardgameList() {
		List<TopBoardgameDto> top10BoardgameList = queryFactory.select(Projections.constructor(TopBoardgameDto.class, boardgame.id,boardgame.name)).from(searchClickLog)
				.join(searchClickLog.boardgame,boardgame)
				.orderBy(searchClickLog.clickCount.desc())
				.limit(10)
				.fetch();
		return top10BoardgameList;
	}

}

