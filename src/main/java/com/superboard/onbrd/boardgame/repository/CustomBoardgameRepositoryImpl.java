package com.superboard.onbrd.boardgame.repository;

import static com.superboard.onbrd.boardgame.entity.QBoardGame.*;
import static com.superboard.onbrd.boardgame.entity.QNonSearchClickLog.*;
import static com.superboard.onbrd.boardgame.entity.QSearchClickLog.*;
import static com.superboard.onbrd.global.util.PagingUtil.*;
import static com.superboard.onbrd.tag.entity.QBoardGameTag.*;
import static com.superboard.onbrd.tag.entity.QTag.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.superboard.onbrd.boardgame.dto.BoardGameDetailDto;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagRequest;
import com.superboard.onbrd.boardgame.dto.BoardGameSearchDetail;
import com.superboard.onbrd.boardgame.dto.TopBoardgameDto;
import com.superboard.onbrd.global.dto.OnbrdSliceInfo;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;
import com.superboard.onbrd.tag.entity.Tag;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomBoardgameRepositoryImpl implements CustomBoardgameRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public OnbrdSliceResponse<BoardGameSearchDetail> searchBoardgameList(
		BoardgameSearchByTagRequest boardgameSearchByTagRequest, String imagePath) {

		List<BoardGameSearchDetail> content = queryFactory
			.select(Projections.fields(BoardGameSearchDetail.class,
				boardGame.id,
				boardGame.name,
				boardGame.image
			))
			.from(boardGameTag)
			.join(boardGameTag.boardGame, boardGame)
			.join(boardGameTag.tag, tag)
			.where(boardgameNameLike(boardgameSearchByTagRequest.getName()),
				tag.id.in(boardgameSearchByTagRequest.getTagIds()))
			.orderBy(tag.id.asc())
			.offset(boardgameSearchByTagRequest.getOffset())
			.limit(boardgameSearchByTagRequest.getLimit() + 1)
			.fetch();

		OnbrdSliceInfo pageInfo = getSliceInfo(content, boardgameSearchByTagRequest.getLimit());

		for (var boardgame : content) {
			String imageName = boardgame.getImage();
			boardgame.setImage(imagePath + imageName);
		}

		return new OnbrdSliceResponse<>(pageInfo, content);
	}

	private BooleanExpression tagIsIn(List<Long> tagIds) {
		return tagIds.isEmpty() ? null : tag.id.in(tagIds);
	}

	private BooleanExpression boardgameNameLike(String boardgameName) {
		return StringUtils.hasText(boardgameName) ? null : boardGame.name.like(boardgameName);
	}

	@Override
	public BoardGameDetailDto selectBoardgameInfo(Long boardgameId) {
		BoardGameDetailDto boardgameDetail = queryFactory
			.select(
				Projections.constructor(BoardGameDetailDto.class, boardGame.id, boardGame.name, boardGame.description,
					boardGame.image, boardGame.favoriteCount))
			.from(boardGame).where(boardGame.id.eq(boardgameId)).fetchOne();
		List<Tag> tagList = queryFactory.select(tag).distinct().from(boardGameTag).join(boardGameTag.tag, tag)
			.where(boardGameTag.boardGame.id.eq(boardgameId)).fetch();
		boardgameDetail.setTagList(tagList);
		return boardgameDetail;
	}

	@Override
	@Transactional(readOnly = true)
	public Long updateFavoriteCount(Long id) {
		long count = queryFactory.update(boardGame)
			.where(boardGame.id.eq(id))
			.set(boardGame.favoriteCount, boardGame.favoriteCount.add(1)).execute();
		return count;
	}

	@Override
	public OnbrdSliceResponse<BoardGameSearchDetail> selectRecommandBoardgameList(
		BoardgameSearchByTagRequest boardgameSearchByTagRequest, String imagePath) {

		LocalDateTime startDate = LocalDateTime.now().minusDays(30);

		List<BoardGameSearchDetail> content = queryFactory
			.select(Projections.fields(BoardGameSearchDetail.class,
				boardGame.id,
				boardGame.name,
				boardGame.image
			))
			.from(nonSearchClickLog)
			.join(nonSearchClickLog.boardGame, boardGame)
			.where(nonSearchClickLog.clickAt.after(startDate))
			.orderBy(boardGame.clickCount.desc())
			.offset(boardgameSearchByTagRequest.getOffset())
			.limit(boardgameSearchByTagRequest.getLimit() + 1)
			.fetch();

		OnbrdSliceInfo pageInfo = getSliceInfo(content, boardgameSearchByTagRequest.getLimit());

		for (var boardgame : content) {
			String imageName = boardgame.getImage();
			boardgame.setImage(imagePath + imageName);
		}

		return new OnbrdSliceResponse<>(pageInfo, content);
	}

	@Override
	@Transactional(readOnly = true)
	public void updateClickCount(Long id) {
		queryFactory.update(boardGame).where(boardGame.id.eq(id))
			.set(boardGame.clickCount, boardGame.clickCount.add(1)).execute();
	}

	@Override
	public List<TopBoardgameDto> selectTop10BoardgameList() {
		List<TopBoardgameDto> top10BoardgameList = queryFactory.select(
				Projections.constructor(TopBoardgameDto.class, boardGame.id, boardGame.name)).from(searchClickLog)
			.join(searchClickLog.boardgame, boardGame)
			.orderBy(searchClickLog.clickCount.desc())
			.limit(10)
			.fetch();
		return top10BoardgameList;
	}

}

