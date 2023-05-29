package com.superboard.onbrd.boardgame.repository;

import java.util.List;

import com.superboard.onbrd.boardgame.dto.BoardGameDetailDto;
import com.superboard.onbrd.boardgame.dto.BoardgameSearchByTagRequest;
import com.superboard.onbrd.boardgame.dto.BoardGameSearchDetail;
import com.superboard.onbrd.boardgame.dto.TopBoardgameDto;
import com.superboard.onbrd.global.dto.OnbrdSliceResponse;

public interface CustomBoardgameRepository {

	OnbrdSliceResponse<BoardGameSearchDetail> searchBoardgameList(
		BoardgameSearchByTagRequest boardgameSearchByTagRequest, String imagePath);

	public BoardGameDetailDto selectBoardgameInfo(Long boardgameId);

	OnbrdSliceResponse<BoardGameSearchDetail> selectRecommandBoardgameList(
		BoardgameSearchByTagRequest boardgameSearchByTagRequest, String imagePath);

	public List<TopBoardgameDto> selectTop10BoardgameList(String imagePath);

	public Long updateFavoriteCount(Long id);

	public void updateClickCount(Long id);

}
