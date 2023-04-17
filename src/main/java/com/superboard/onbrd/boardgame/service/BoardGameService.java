package com.superboard.onbrd.boardgame.service;

import java.util.List;

import com.superboard.onbrd.boardgame.dto.BoardGameDetailDto;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagRequest;
import com.superboard.onbrd.boardgame.dto.BoardGameSearchDetail;
import com.superboard.onbrd.boardgame.dto.TopBoardgameDto;
import com.superboard.onbrd.boardgame.entity.BoardGame;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;

public interface BoardGameService {
	OnbrdSliceResponse<BoardGameSearchDetail> searchBoardgameList(
		BoardgameSearchByTagRequest boardgameSearchByTagRequest);

	public BoardGameDetailDto selectBoardgameInfo(Long boardgameId, String referer);

	OnbrdSliceResponse<BoardGameSearchDetail> selectRecommandBoardgameList(
		BoardgameSearchByTagRequest boardgameSearchByTagRequest);

	public List<TopBoardgameDto> selectTop10BoardgameList();

	BoardGame findVerifiedOneById(Long id);

	public Long updateFavoriteCount(Long id);
}
