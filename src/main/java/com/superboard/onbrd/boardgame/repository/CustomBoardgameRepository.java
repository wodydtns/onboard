package com.superboard.onbrd.boardgame.repository;

import java.util.List;

import com.superboard.onbrd.boardgame.dto.*;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;

public interface CustomBoardgameRepository {

	OnbrdSliceResponse<BoardGameSearchDetail> searchBoardgameList(
		BoardgameSearchByTagRequest boardgameSearchByTagRequest);

	public BoardGameDetailDto selectBoardgameInfo(Long boardgameId);

	OnbrdSliceResponse<BoardGameSearchDetail> selectRecommandBoardgameList(
		BoardgameSearchByTagRequest boardgameSearchByTagRequest);

	public List<TopBoardgameDto> selectTop10BoardgameList();

	public Long updateFavoriteCountPlus(Long id);

	public Long updateFavoriteCountMius(Long id);

	public void updateClickCount(Long id);

	public Long updateFavoriteBoardgameLikes(FavoriteBoardGameUpdateCommand command);

}
