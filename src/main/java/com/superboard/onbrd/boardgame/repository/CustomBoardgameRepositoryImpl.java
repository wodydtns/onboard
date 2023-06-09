package com.superboard.onbrd.boardgame.repository;

import static com.superboard.onbrd.boardgame.entity.QBoardGame.*;
import static com.superboard.onbrd.boardgame.entity.QNonSearchClickLog.*;
import static com.superboard.onbrd.boardgame.entity.QSearchClickLog.*;
import static com.superboard.onbrd.global.util.PagingUtil.*;
import static com.superboard.onbrd.member.entity.QMember.member;
import static com.superboard.onbrd.tag.entity.QBoardGameTag.*;
import static com.superboard.onbrd.tag.entity.QTag.*;
import static com.superboard.onbrd.review.entity.QReview.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.superboard.onbrd.boardgame.dto.*;
import com.superboard.onbrd.review.dto.review.ReviewByBoardgameDetail;
import com.superboard.onbrd.review.dto.review.ReviewByFavoriteCountDetail;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

		BooleanExpression nameExpr = boardgameNameLike(boardgameSearchByTagRequest.getName());
		BooleanExpression tagExpr = tagIsIn(boardgameSearchByTagRequest.getTagIds());

		// FIXME : boardgame : boardgameTag  - 1 : N -> groupby로 해결했으므로 제대로 수정 필요
		List<BoardGameSearchDetail> content = queryFactory
				.select(Projections.fields(BoardGameSearchDetail.class,
						boardGame.id,
						boardGame.name,
						boardGame.image
				))
				.from(boardGameTag)
				.join(boardGameTag.boardGame, boardGame)
				.join(boardGameTag.tag, tag)
				.where(nameExpr, tagExpr)
				.groupBy(boardGame.id,boardGame.name,boardGame.image)
				.orderBy(boardGame.id.asc())
				.offset(boardgameSearchByTagRequest.getOffset())
				.limit(boardgameSearchByTagRequest.getLimit() + 1)
				.fetch();

		List<BoardGameGroupByGrade> boardGameGroupByGrades = queryFactory
				.select(Projections.constructor(BoardGameGroupByGrade.class,
						boardGame.id.as("id"),
						review.grade.avg().round().as("grade")
				))
				.from(review)
				.join(review.boardgame, boardGame)
				.groupBy(boardGame.id)
				.orderBy(boardGame.id.asc())
				.fetch();
		// FIXME : 임시 코드 - getSliceInfo 메서드 return 시 content 내용이 사라짐 - 임시로 list를 복사하는 형태로 구현
		List<BoardGameSearchDetail> pageContent =  new ArrayList<>(content);
		OnbrdSliceInfo pageInfo = getSliceInfo(pageContent, boardgameSearchByTagRequest.getLimit());

		for (var boardgame : content) {
			String imageName = boardgame.getImage();

			boardgame.setImage(imagePath + imageName);

			// Compare id and set the grade
			for (var boardGameGrade : boardGameGroupByGrades) {
				if (boardgame.getId().equals(boardGameGrade.getId())) {
					boardgame.setGrade((float) boardGameGrade.getGrade());
					break;
				}
			}
		}

		return new OnbrdSliceResponse<BoardGameSearchDetail>(pageInfo, content);
	}

	private BooleanExpression tagIsIn(List<Long> tagIds) {
		return (tagIds != null && !tagIds.isEmpty()) ? tag.id.in(tagIds) : null;
	}

	private BooleanExpression boardgameNameLike(String boardgameName) {
		return StringUtils.hasText(boardgameName) ?  boardGame.name.like(boardgameName) : null ;
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

		BooleanExpression tagExpr = tagIsIn(boardgameSearchByTagRequest.getTagIds());

		LocalDateTime startDate = LocalDateTime.now().minusDays(30);

		List<BoardGameSearchDetail> content = queryFactory
			.select(Projections.fields(BoardGameSearchDetail.class,
				boardGame.id,
				boardGame.name,
				boardGame.image
			))
			.from(nonSearchClickLog)
			.join(nonSearchClickLog.boardgame, boardGame)
			.where(nonSearchClickLog.clickAt.after(startDate) , tagExpr)
			.orderBy(boardGame.clickCount.desc())
			.offset(boardgameSearchByTagRequest.getOffset())
			.limit(boardgameSearchByTagRequest.getLimit() + 1)
			.fetch();

		List<BoardGameGroupByGrade> boardGameGroupByGrades = queryFactory
				.select(Projections.constructor(BoardGameGroupByGrade.class,
						boardGame.id.as("id"),
						review.grade.avg().round().as("grade")
				))
				.from(review)
				.join(review.boardgame, boardGame)
				.groupBy(boardGame.id)
				.orderBy(boardGame.id.asc())
				.fetch();

		// FIXME : 임시 코드 - getSliceInfo 메서드 return 시 content 내용이 사라짐 - 임시로 list를 복사하는 형태로 구현
		List<BoardGameSearchDetail> pageContent =  new ArrayList<>(content);
		OnbrdSliceInfo pageInfo = getSliceInfo(pageContent, boardgameSearchByTagRequest.getLimit());

		for (var boardgame : content) {
			String imageName = boardgame.getImage();
			boardgame.setImage(imagePath + imageName);

			// Compare id and set the grade
			for (var boardGameGrade : boardGameGroupByGrades) {
				if (boardgame.getId().equals(boardGameGrade.getId())) {
					boardgame.setGrade((float) boardGameGrade.getGrade());
					break;
				}
			}
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
	public List<TopBoardgameDto> selectTop10BoardgameList(String imagePath) {
		List<TopBoardgameDto> top10BoardgameList = queryFactory.select(
				Projections.constructor(TopBoardgameDto.class, boardGame.id, boardGame.name,boardGame.image)).from(searchClickLog)
			.join(searchClickLog.boardgame, boardGame)
			.orderBy(searchClickLog.clickCount.desc())
			.limit(10)
			.fetch();
		for (var boardgame : top10BoardgameList) {
			String imageName = boardgame.getImage();
			boardgame.setImage(imagePath + imageName);
		}
		return top10BoardgameList;
	}

}

