package com.superboard.onbrd.boardgame.service;

import java.util.List;

import com.superboard.onbrd.boardgame.dto.*;
import com.superboard.onbrd.boardgame.entity.BoardGame;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;

public interface BoardGameService {
	OnbrdSliceResponse<BoardGameSearchDetail> searchBoardgameList(
		BoardgameSearchByTagRequest boardgameSearchByTagRequest);

	BoardGameDetailDto selectBoardgameInfo(Long boardgameId, String referer);

	OnbrdSliceResponse<BoardGameSearchDetail> selectRecommandBoardgameList(
		BoardgameSearchByTagRequest boardgameSearchByTagRequest);

	List<TopBoardgameDto> selectTop10BoardgameList();

	BoardGame findVerifiedOneById(Long id);

	Long updateFavoriteCount(Long id);

	public Long createFavoriteBoardgameLikeOfDeleteIfExist(FavoriteBoardGameUpdateCommand command);
}
