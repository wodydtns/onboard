package com.superboard.onbrd.boardgame.service;

import java.util.List;

import com.superboard.onbrd.boardgame.dto.BoardgameDetailDto;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagRequest;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchDetail;
import com.superboard.onbrd.boardgame.dto.TopBoardgameDto;
import com.superboard.onbrd.boardgame.entity.Boardgame;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;

public interface BoardGameService {
	OnbrdSliceResponse<BoardgameSearchDetail> searchBoardgameList(
		BoardgameSearchByTagRequest boardgameSearchByTagRequest);

	public BoardgameDetailDto selectBoardgameInfo(Long boardgameId, String referer);

	OnbrdSliceResponse<BoardgameSearchDetail> selectRecommandBoardgameList(
		BoardgameSearchByTagRequest boardgameSearchByTagRequest);

	public List<TopBoardgameDto> selectTop10BoardgameList();

	Boardgame findVerifiedOneById(Long id);

	public Long updateFavoriteCount(Long id);
}
